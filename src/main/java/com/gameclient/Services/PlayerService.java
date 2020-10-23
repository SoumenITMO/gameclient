package com.gameclient.Services;

import com.gameclient.Components.Scheduler;
import com.gameclient.Dto.*;
import com.gameclient.Helper.HelperMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    HelperMethods helperMethods;

    @Autowired
    Environment environment;

    @Autowired
    Scheduler scheduler;

    private LoginResponseDto loginResponse = new LoginResponseDto();
    private List<LoginResponseDto> loggedUsers = new ArrayList<>();
    public List<BettingData> bettingData = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public ResponseEntity gameLogin(PlayerLoginDto playerLoginDto) {

        if(!loggedUsers.stream().anyMatch(loggedUser -> loggedUser.getPlayerUsername().equals(playerLoginDto.getUsername()))) {
            loginResponse = helperMethods.login(environment.getProperty("gameserver.url").concat("login"), playerLoginDto);
        }
        if(loginResponse.getPlayerUsername() != null) {
            if(!loggedUsers.stream().filter(loginData -> loginData.getPlayerUsername()
                    .equals(loginResponse.getPlayerUsername())).findFirst().isPresent()) {
                loggedUsers.add(loginResponse);
            }
            return new ResponseEntity(loginResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Login Failed.", HttpStatus.OK);
        }
    }

    public BettingResponse bet(BettingDto bettingInfo) {

        float walletBalance = 0.0f;

        if(loggedUsers.stream().filter(getLoggedInUser -> getLoggedInUser.getPlayerUsername()
                .equals(bettingInfo.getUsername())).findFirst().isPresent()) {
            walletBalance = (float)loggedUsers.stream()
                    .filter(getLoggedInUser -> getLoggedInUser.getPlayerUsername()
                    .equals(bettingInfo.getUsername()))
                    .mapToDouble(LoginResponseDto::getWalletBalance).findFirst().getAsDouble();

            if(loggedUsers.stream()
                    .filter(username -> username.getPlayerUsername().equals(bettingInfo.getUsername()))
                    .filter(username -> username.getProfileStatus() == 1)
                    .findFirst().isPresent()) {
                logger.error(bettingInfo.getUsername().concat(" ->> PROFILE HAS BEEN BLACK LISTED. ").concat(String.valueOf(bettingInfo.getBetAmount())));
                return new BettingResponse(0L, 101, 0, bettingInfo.getBetAmount(), walletBalance);
            }
            else if(loggedUsers.stream()
                    .filter(username -> username.getPlayerUsername().equals(bettingInfo.getUsername()))
                    .filter(username -> username.getWalletBalance() < bettingInfo.getBetAmount())
                    .findFirst().isPresent()) {
                logger.error(bettingInfo.getUsername().concat(" ->> HAS LOW WALLET BALANCE. BET AMOUNT ->> ").concat(String.valueOf(bettingInfo.getBetAmount())));
                return new BettingResponse(0L, 102, 0, bettingInfo.getBetAmount(), walletBalance);
            }
            else if(loggedUsers.stream()
                    .filter(username -> username.getPlayerUsername().equals(bettingInfo.getUsername()))
                    .filter(username -> username.getBetAmount() < bettingInfo.getBetAmount())
                    .findFirst().isPresent()) {
                logger.error(bettingInfo.getUsername().concat(" ->> HAS CROSSED THAN ALLOWED BET AMOUNT. ").concat(String.valueOf(bettingInfo.getBetAmount())));
                return new BettingResponse(0L, 103, 0, bettingInfo.getBetAmount(), walletBalance);
            }
            else {
                Long transactionId = helperMethods.genRandomNumber();
                bettingData.add(new BettingData(transactionId, bettingInfo.getUsername(), bettingInfo.getBetAmount()));
                loggedUsers = helperMethods.updateUserWalletData(bettingData, loggedUsers);
                scheduler.setFinalBettingData(loggedUsers);
                scheduler.setTruePlayersProfile();
                return new BettingResponse(transactionId, 200, 0, bettingInfo.getBetAmount(), helperMethods.getWalletBalance());
            }
        }
        else {
            return new BettingResponse(0L, 104, 0, bettingInfo.getBetAmount(), walletBalance);
        }
    }

    public String gameLogout(PlayerLogoutDto playerLogout) {

        loggedUsers = loggedUsers.stream().filter(getLoggedInUsers -> !getLoggedInUsers.getPlayerUsername()
                .equals(playerLogout.getPlayerUsername())).collect(Collectors.toList());
        return helperMethods.logout(environment.getProperty("gameserver.url").concat("logout"), playerLogout);
    }
}
