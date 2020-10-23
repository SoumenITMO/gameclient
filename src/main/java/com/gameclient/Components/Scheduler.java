package com.gameclient.Components;

import com.gameclient.Dto.LoginResponseDto;
import com.gameclient.Helper.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {

    private List<LoginResponseDto> loggedinProfiles = new ArrayList<>();
    private String updatePlayerDataURL = "";
    private boolean updatePlayersProfile = false;

    @Autowired
    HelperMethods helperMethods;

    @Autowired
    Environment environment;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        if(loggedinProfiles.size() > 0 && updatePlayersProfile) {
            updatePlayerDataURL = environment.getProperty("gameserver.url");
            helperMethods.updateLoggedInUsers(updatePlayerDataURL.concat("updateprofiles"), loggedinProfiles);
            updatePlayersProfile = false;
        }
    }

    public void setFinalBettingData(List<LoginResponseDto> loggedinProfiles) {
        this.loggedinProfiles = loggedinProfiles;
    }

    public void setTruePlayersProfile() {
        updatePlayersProfile = true;
    }
}
