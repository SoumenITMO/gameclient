package com.gameclient.Helper;

import com.gameclient.Dto.BettingData;
import com.gameclient.Dto.PlayerLoginDto;
import com.gameclient.Dto.LoginResponseDto;
import com.gameclient.Dto.PlayerLogoutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HelperMethods {

    private float walletBalance = 0.0f;
    private final Logger logger = LoggerFactory.getLogger(HelperMethods.class);

    public LoginResponseDto login(String loginUrl, PlayerLoginDto playerLoginDto) {
        System.out.println(loginUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PlayerLoginDto> request = new HttpEntity<>(playerLoginDto, httpHeaders);

            return restTemplate.postForObject(loginUrl, request, LoginResponseDto.class);
        } catch (ResourceAccessException resourceAccessException) {
            logger.error("Cannot Reach to server...");
        }
        return new LoginResponseDto();
    }

    public String logout(String url, PlayerLogoutDto playerLogoutDto) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PlayerLogoutDto> request = new HttpEntity<>(playerLogoutDto, httpHeaders);

            return restTemplate.postForObject(url, request, String.class);
        } catch (ResourceAccessException resourceAccessException) {
            logger.error("Cannot Reach to server...");
        }
        return null;
    }

    public String updateLoggedInUsers(String url, List<LoginResponseDto> updatedPlayerData) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<LoginResponseDto>> request = new HttpEntity<>(updatedPlayerData, httpHeaders);

            return restTemplate.postForObject(url, request, String.class);
        } catch (ResourceAccessException resourceAccessException) {
            logger.error("Cannot Reach to server...");
        }
        return null;
    }

    public List<LoginResponseDto> updateUserWalletData(List<BettingData> bettingData, List<LoginResponseDto> loggedUsers) {

        List<LoginResponseDto> loggedInUsersNewData = new ArrayList<>();

        loggedUsers.forEach(getLoggedUser -> {
            if(bettingData.stream().anyMatch(getBettingUser -> getBettingUser.getUsername()
                    .equals(getLoggedUser.getPlayerUsername()))) {

                LoginResponseDto updateLoggedInUserData = new LoginResponseDto();

                walletBalance = getLoggedUser.getWalletBalance() - bettingData.stream().filter(getPlayerBettingData -> getPlayerBettingData.getUsername()
                        .equals(getLoggedUser.getPlayerUsername()))
                        .map(BettingData::getTransactionAmount).reduce(Float::sum).get();
                round(walletBalance, 2);
                updateLoggedInUserData.setPlayerUsername(getLoggedUser.getPlayerUsername());
                updateLoggedInUserData.setBetAmount(getLoggedUser.getBetAmount());
                updateLoggedInUserData.setPlayerName(getLoggedUser.getPlayerName());
                updateLoggedInUserData.setProfileStatus(getLoggedUser.getProfileStatus());
                updateLoggedInUserData.setWalletBalance(walletBalance);
                loggedInUsersNewData.add(updateLoggedInUserData);
                bettingData.remove(bettingData.stream().filter(getBettingUser -> getBettingUser.getUsername()
                        .equals(getLoggedUser.getPlayerUsername())).collect(Collectors.toList()).get(0));
            }
            else {
                loggedInUsersNewData.add(getLoggedUser);
            }
        });
        return loggedInUsersNewData;
    }

    public float getWalletBalance() {
        return walletBalance;
    }

    public Long genRandomNumber() {

        int start = 1000000000;
        long end = 9999999999L;
        Random random = new Random();
        return (long)((end - (long)start + 1) * random.nextDouble()) + (long)start;
    }

    private void round(float value, int places) {

        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bigDecimal = new BigDecimal(Float.toString(value));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        walletBalance = bigDecimal.floatValue();
    }
}
