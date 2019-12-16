package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import javafx.collections.transformation.TransformationList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("list_1", "Morty", true);
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("board_1", "Rixty", trelloListsDto);
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(1, trelloBoards.size());
        assertEquals("board_1", trelloBoards.get(0).getId());
        assertEquals("Rixty", trelloBoards.get(0).getName());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        TrelloList trelloList = new TrelloList("list_1", "Morty", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("board_1", "Rixty", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(1, trelloBoardsDto.size());
        assertEquals("board_1", trelloBoardsDto.get(0).getId());
        assertEquals("Rixty", trelloBoardsDto.get(0).getName());
    }

    @Test
    public void mapToLists() {
        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("list_1", "Morty", true));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToLists(trelloListsDto);
        List<TrelloList> trelloListsEmpty = trelloMapper.mapToLists(Collections.emptyList());

        //Then
        assertEquals(1, trelloLists.size());
        assertTrue(trelloLists.contains(new TrelloList("list_1", "Morty", true)));
        assertEquals(0, trelloListsEmpty.size());
    }

    @Test
    public void mapToListsDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("list_2", "Ricky", true));

        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListsDto(trelloLists);
        List<TrelloListDto> trelloListsDtoEmpty = trelloMapper.mapToListsDto(Collections.emptyList());

        //Then
        assertEquals(1, trelloListsDto.size());
        assertEquals("list_2", trelloListsDto.get(0).getId());
        assertEquals("Ricky", trelloListsDto.get(0).getName());
        assertEquals(0, trelloListsDtoEmpty.size());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto( "card_1", "Morty adventure", "posl", "idl");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(new TrelloCard("card_1", "Morty adventure", "posl", "idl"), trelloCard);
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard( "card_1", "Morty adventure", "posl", "idl");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(new TrelloCardDto("card_1", "Morty adventure", "posl", "idl"), trelloCardDto);
    }
}