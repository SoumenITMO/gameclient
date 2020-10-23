package com.gameclient.Controllers;

import com.gameclient.Dto.BettingDto;
import com.gameclient.Dto.BettingResponse;
import com.gameclient.Dto.PlayerLoginDto;
import com.gameclient.Dto.PlayerLogoutDto;
import com.gameclient.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @RequestMapping(value = "gamelogin", method = RequestMethod.POST)
    public ResponseEntity gameLogin(@RequestBody PlayerLoginDto playerLoginDto) {

        return playerService.gameLogin(playerLoginDto);
    }

    @RequestMapping(value = "gamelogout", method = RequestMethod.POST)
    public String gameLogout(@RequestBody PlayerLogoutDto playerLogoutDto) {

        return playerService.gameLogout(playerLogoutDto);
    }

    @RequestMapping(value = "bet", method = RequestMethod.POST)
    public ResponseEntity<BettingResponse> bet(@RequestBody BettingDto betting) {

        return new ResponseEntity<>(playerService.bet(betting), HttpStatus.OK);
    }
}
