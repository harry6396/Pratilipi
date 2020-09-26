package com.coder.pratilipi.controller;

import org.springframework.stereotype.Service;

import com.coder.pratilipi.Model.GameRequest;
import com.coder.pratilipi.Model.Response;

@Service
public class GameService {

	static int [][]move = new int[6][7];
	static int moveNumber = 1;
	
	public Response updateGameMove(GameRequest request) {
		
		Response response = new Response();
		
		try {
			int requestColumn = Integer.parseInt(request.getSelectedColumn()), requestedPlayerCode = 1;
			boolean isMoveAvailable = true;
			
			String requestedPlayer = request.getPlayerName(), winner = "";
			
			if(requestedPlayer.toLowerCase().equals("yellow")) {
				if(moveNumber%2 == 0) {
					response.setResponse("Invalid move! Yellow can only have odd chances");
					response.setStatus("Success");
					return response;
				}
			}
			
			if(requestedPlayer.toLowerCase().equals("red")) {
				if(moveNumber%2 == 1) {
					response.setResponse("Invalid move! Red can only have even chances");
					response.setStatus("Success");
					return response;
				}
				requestedPlayerCode = 2;
			}
						
			if(requestColumn < 7 && requestColumn >= 0 ) {
				
				for(int i=5; i>=0; i--) {
					if(move[0][requestColumn] != 0) {
						response.setResponse("Invalid move");
						response.setStatus("Success");
						return response;
					}
					else if(move[i][requestColumn] == 0) {
						move[i][requestColumn] = requestedPlayerCode;
						response.setResponse("Updated column " + requestColumn);
						response.setStatus("Success");
						moveNumber++;
						break;
					}
				}
				
				isMoveAvailable = checkIfMoveAvailable(move);
				
				if(isMoveAvailable == Boolean.FALSE) {
					
					winner = checkWinner(move);
					
					response.setResponse(winner);
					response.setStatus("Success");
					
					return response;
				}else {
					return response;
				}
				
			}else {
				response.setResponse("Invalid column selected");
				response.setStatus("Success");
				return response;
			}
		}
		catch(Exception ex) {
			response.setResponse("Invalid number");
			response.setStatus("Failed");
		}
		return response;
	}
	
	private boolean checkIfMoveAvailable(int [][]move) {
		for(int iRowCounter = 0; iRowCounter < move.length; iRowCounter++) {
			for(int iColumnCounter=0; iColumnCounter < move[iRowCounter].length; iColumnCounter++) {
				if(move[iRowCounter][iColumnCounter] == 0) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	private String checkWinner(int [][]move) {
		
		int totalYellowCount = 0, totalRedCount = 0,  yellowCount = 0, redCount = 0;
		
		String winner = "Yellow wins";
		
// check for row
		
		for(int iRowCounter = 0; iRowCounter < move.length; iRowCounter++) {
			yellowCount = 0;
			redCount = 0;
			for(int iColumnCounter=0; iColumnCounter < move[iRowCounter].length; iColumnCounter++) {
				
				if(move[iRowCounter][iColumnCounter] == 1) {
					yellowCount++;
					redCount = 0;
				}
				else if(move[iRowCounter][iColumnCounter] == 2){
					redCount++;
					yellowCount = 0;
				}
				
				if(yellowCount == 4) 
				{
					totalYellowCount++;
				}
				else if(redCount == 4) 
				{
					totalRedCount++;
				}
				
			}
		}
		
// reseting counters
		
		yellowCount = 0;
		redCount = 0;

// check for column 
		
		for(int iColumnCounter = 0; iColumnCounter < 7; iColumnCounter++) {
			yellowCount = 0;
			redCount = 0;
			for(int iRowCounter=0; iRowCounter < 6; iRowCounter++) {
				
				if(move[iRowCounter][iColumnCounter] == 1) {
					yellowCount++;
					redCount = 0;
				}
				else if(move[iRowCounter][iColumnCounter] == 2){
					redCount++;
					yellowCount = 0;
				}
				
				if(yellowCount == 4) 
				{
					totalYellowCount++;
				}
				else if(redCount == 4) 
				{
					totalRedCount++;
				}
				
			}
		}

// reseting counters
		
		yellowCount = 0;
		redCount = 0;
		
// check for top-bottom diagonal
		
		for(int iCounter = 0; iCounter < 7; iCounter++) {
			
			if(move[iCounter][iCounter] == 1) {
				yellowCount++;
				redCount = 0;
			}
			else if(move[iCounter][iCounter] == 2) {
				redCount++;
				yellowCount = 0;
			}
			
			if(yellowCount == 4) 
			{
				totalYellowCount++;
			}
			else if(redCount == 4) 
			{
				totalRedCount++;
			}
			
		}
		
// reseting counters
		
		yellowCount = 0;
		redCount = 0;
		
// check for bottom-top diagonal
		
		for(int iCounter = 6; iCounter >= 0; iCounter--) 
		{
			
			if(move[iCounter][iCounter] == 1) {
				yellowCount++;
				redCount = 0;
			}
			else if(move[iCounter][iCounter] == 2) {
				redCount++;
				yellowCount = 0;
			}
			
			if(yellowCount == 4) 
			{
				totalYellowCount++;
			}
			else if(redCount == 4) 
			{
				totalRedCount++;
			}
			
		}
		
// check for winner
		
		if(totalYellowCount < totalRedCount) 
		{
			winner = "Red wins";
		}
		else if(totalYellowCount == totalRedCount) 
		{
			winner = "Draw";
		}
		
		move = new int[6][7];
		moveNumber = 1;
		return winner;
	}
}
