package fr.triedge.sekai.client.controller;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.log4j.Logger;

import fr.triedge.sekai.common.network.SekaiMessage;
import fr.triedge.sekai.common.network.SekaiMessageFactory;
import fr.triedge.sekai.common.network.SekaiProtocol;
import fr.triedge.sekai.common.network.SekaiSession;

public class CommandLine implements Runnable{

	private String line = "";
	static final Logger log = Logger.getLogger(CommandLine.class);
	private SekaiSession session;
	
	public CommandLine(SekaiSession session) {
		super();
		this.session = session;
	}

	@Override
	public void run() {
		log.debug("START: run()");
		Scanner scan = new Scanner(System.in);
		while (!line.equals("exit")) {
			System.out.print("> ");
			line = scan.nextLine();
			parseLine(line);
		}
		scan.close();
		log.debug("END: run()");
	}

	private void parseLine(String line) {
		String cmd = line.split(" ")[0];
		switch (cmd) {
		case "login":
			processLogin(cmd);
			break;
		case "adduser":
			processAddUser(cmd);
			break;
		case "help":
			System.out.println(" -> login user pwd");
			System.out.println(" -> adduser user pwd");
			break;
		default:
			break;
		}
	}

	private void processAddUser(String cmd) {
		String[] args = cmd.split(" ");
		String user = args[1];
		String pwd = args[2];
		SekaiMessage mes_ask_add = SekaiMessageFactory.createMesAskCreateUserToServer(user, pwd);
		System.out.println("Creation of user...");
		try {
			System.out.println("Sending message...");
			SekaiMessage ans_add = session.sendMessageWithResponse(mes_ask_add);
			System.out.println("Message sent");
			if (ans_add.code == SekaiProtocol.SERVER_RESPOND_CREATE_USER_OK){
				System.out.println("CREATION OK");
			}else{
				System.out.println("CREATION FAILED");
			}
		} catch (ClassNotFoundException | IOException e) {
			log.error("Failed to send message", e);
		}
	}

	private void processLogin(String cmd) {
		String[] args = cmd.split(" ");
		String user = args[1];
		String pwd = args[2];
		SekaiMessage mes_ask_login = SekaiMessageFactory.createMesAskLoginToServer(user, pwd);
		System.out.println("Login into server...");
		try {
			System.out.println("Sending message...");
			SekaiMessage ans_login = session.sendMessageWithResponse(mes_ask_login);
			System.out.println("Message sent");
			if (ans_login.code == SekaiProtocol.SERVER_RESPOND_LOGIN_OK){
				System.out.println("LOGIN OK");
				System.out.println("Character list:");
				//Get the user's characters
				for (Entry<String, Object> param : ans_login.getParams().entrySet()){
					System.out.println(param.getKey());
				}
				System.out.println("Choose a character");
				
				
			}else{
				System.out.println("LOGIN FAILED");
			}
		} catch (ClassNotFoundException | IOException e) {
			log.error("Failed to send message", e);
		}
	}

	public SekaiSession getSession() {
		return session;
	}

	public void setSession(SekaiSession session) {
		this.session = session;
	}

}
