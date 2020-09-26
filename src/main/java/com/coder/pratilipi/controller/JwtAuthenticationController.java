package com.coder.pratilipi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coder.pratilipi.Model.GameRequest;
import com.coder.pratilipi.Model.Response;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class JwtAuthenticationController {
	
	@Autowired
	private GameService gameService;

    @RequestMapping(value = "/public/move", method = RequestMethod.POST)
    public Response updateGameMove(@RequestBody GameRequest request) throws SQLException {
    	Response response = new Response();
    	if(request.getPlayerName() != null && request.getSelectedColumn() != null) 
    	{
    		return gameService.updateGameMove(request);
    	}
    	response.setResponse("Incorrect request");
    	response.setStatus("Failed");
    	return response;
    }
}
