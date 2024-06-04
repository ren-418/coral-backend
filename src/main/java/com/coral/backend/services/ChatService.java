package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.*;
import com.coral.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
  @Autowired
  private ChatRoomRepository chatRoomRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SessionRepository sessionRepository;
  @Autowired
  private EnterpriseUserRepository enterpriseUserRepository;
  @Autowired
  private InvestorUserRepository investorUserRepository;
  @Autowired
  private ChatMessageRepository chatMessageRepository;
  public ResponseEntity<Object> sendMessage(MessageDTO requestBody) {
    Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSenderSessionToken());
    if (optionalSession.isEmpty()) {
      return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
    }

    User sender = optionalSession.get().getUser();
    InvestorUser investorUser;
    EnterpriseUser enterpriseUser;

    switch (sender.getUserType()) {
      case "investor" -> {
        investorUser = investorUserRepository.findInvestorUserByUserId(sender.getUserId());
        enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(requestBody.getReceiverId());
      }
      case "enterprise" -> {
        investorUser = investorUserRepository.findInvestorUserByUserId(requestBody.getReceiverId());
        enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(sender.getUserId());
      }
      default -> {
        return new ResponseEntity<>("Sender user type is not valid", HttpStatus.NOT_FOUND);
      }
    }

    Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomByInvestorAndEnterprise(investorUser, enterpriseUser);
    if (chatRoom.isEmpty()) {
      ChatRoom newChatRoom = new ChatRoom();
      newChatRoom.setEnterprise(enterpriseUser);
      newChatRoom.setInvestor(investorUser);

      List<ChatMessage> chatMessageList = new ArrayList<>();

      ChatMessage newMessage = new ChatMessage();
      newMessage.setMessage(requestBody.getMessage());
      newMessage.setSenderId(sender.getUserId());

      // Get the current date and time
      LocalDateTime currentDateTime = LocalDateTime.now();

      // Convert the current date and time to a string
      String dateTimeString = currentDateTime.toString();

      newMessage.setTimeStamp(dateTimeString);


      chatMessageList.add(newMessage);
      newChatRoom.setMessages(chatMessageList);

      chatMessageRepository.save(newMessage);
      chatRoomRepository.save(newChatRoom);
    }
    else {
      ChatRoom existingChatRoom = chatRoom.get();
      List<ChatMessage> oldMessages = existingChatRoom.getMessages();

      ChatMessage newMessage = new ChatMessage();
      newMessage.setMessage(requestBody.getMessage());
      newMessage.setSenderId(sender.getUserId());

      // Get the current date and time
      LocalDateTime currentDateTime = LocalDateTime.now();

      // Convert the current date and time to a string
      String dateTimeString = currentDateTime.toString();

      newMessage.setTimeStamp(dateTimeString);

      chatMessageRepository.save(newMessage);
      oldMessages.add(newMessage);
      existingChatRoom.setMessages(oldMessages);
    }
    return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
  }

  public ResponseEntity<Object> getMessages(GetMessagesDTO requestBody) {
    Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSenderSessionToken());
    if (optionalSession.isEmpty()) {
      return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
    }

    User sender = optionalSession.get().getUser();
    InvestorUser investorUser;
    EnterpriseUser enterpriseUser;

    switch (sender.getUserType()) {
      case "investor" -> {
        investorUser = investorUserRepository.findInvestorUserByUserId(sender.getUserId());
        enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(requestBody.getReceiverId());
      }
      case "enterprise" -> {
        investorUser = investorUserRepository.findInvestorUserByUserId(requestBody.getReceiverId());
        enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(sender.getUserId());
      }
      default -> throw new RuntimeException("Not a valid user type");
    }

    Optional<ChatRoom> chatRoom = getChatRoom(investorUser, enterpriseUser);

    ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
    chatRoomDTO.setMessages(chatRoom.get().getMessages());
    chatRoomDTO.setEnterpriseDTO(enterpriseUser.toDTO());
    chatRoomDTO.setInvestorDTO(investorUser.toDTO());

    return new ResponseEntity<>(chatRoomDTO, HttpStatus.OK);
  }

  private Optional<ChatRoom> getChatRoom(InvestorUser investorUser, EnterpriseUser enterpriseUser) {
    return chatRoomRepository.findChatRoomByInvestorAndEnterprise(investorUser, enterpriseUser);
  }

  public ResponseEntity<Object> getChats(CheckSessionDTO requestBody) {
    Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
    if (optionalSession.isEmpty()) {
      return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
    }
    User sender = optionalSession.get().getUser();

    List<ChatRoom> allChatRooms;
    List<ChatPreviewDTO> chatPreviews = new ArrayList<>();
    switch (sender.getUserType()) {
      case "investor" -> {
        allChatRooms = chatRoomRepository.findAllByInvestor(investorUserRepository.findInvestorUserByUserId(sender.getUserId()));
        for (ChatRoom chatRoom : allChatRooms) {
          EnterpriseUser receiver = chatRoom.getEnterprise();
          ChatMessage lastMessage = chatRoom.getLastMessage();
          chatPreviews.add(new ChatPreviewDTO(decodeImage(receiver.getProfileImage()), receiver.getName(), lastMessage, receiver.getUserId()));
        }
      }
      case "enterprise" -> {
        allChatRooms = chatRoomRepository.findAllByEnterprise(enterpriseUserRepository.findEnterpriseUserByUserId(sender.getUserId()));
        for (ChatRoom chatRoom : allChatRooms) {
          InvestorUser receiver = chatRoom.getInvestor();
          ChatMessage lastMessage = chatRoom.getLastMessage();
          chatPreviews.add(new ChatPreviewDTO(decodeImage(receiver.getProfileImage()), receiver.getName(), lastMessage, receiver.getUserId()));
        }
        System.out.println("in");
      }
      default -> throw new RuntimeException("Not a valid user type");
    }
    AllChatsPreviewDTO chatsPreviewDTO = new AllChatsPreviewDTO(chatPreviews);
    return new ResponseEntity<>(chatsPreviewDTO, HttpStatus.OK);
  }

  private String decodeImage(byte[] byteArray) {
    return new String(byteArray);
  }
}
