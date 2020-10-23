package com.gameclient.Controllers;

import com.gameclient.Dto.BettingDto;
import com.gameclient.Dto.BettingResponse;
import com.gameclient.Dto.PlayerLoginDto;
import com.gameclient.Dto.PlayerLogoutDto;
import com.gameclient.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @RequestMapping(value = "gamelogin", method = RequestMethod.GET)
    public ResponseEntity gameLogin() {

        return playerService.gameLogin(new PlayerLoginDto("Mart85", "hello"));
    }

    @RequestMapping(value = "gamelogout", method = RequestMethod.GET)
    public String gameLogout() {

        return playerService.gameLogout(new PlayerLogoutDto("Mart85"));
    }

    @RequestMapping(value = "bet", method = RequestMethod.GET)
    public ResponseEntity<BettingResponse> bet() {

        return new ResponseEntity<>(playerService.bet(new BettingDto("Mart85", 190.45f)), HttpStatus.OK);
    }
}
