package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrelloValidatorTest {
    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void validateTrelloBoards() {
        //Given
        TrelloBoard boardOne = new TrelloBoard("0", "Ricky Wubbalubbadubdub", new ArrayList<>());
        TrelloBoard boardTwo = new TrelloBoard("1", "Means I am in great pain, please help me", new ArrayList<>());
        List<TrelloBoard> trelloBoardsList = new ArrayList<>();
        trelloBoardsList.add(boardOne);
        trelloBoardsList.add(boardTwo);

        //When
        List<TrelloBoard> validatedTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoardsList);

        List<TrelloBoard> validatedEmptyTrelloBoards = trelloValidator.validateTrelloBoards(new ArrayList<>());

        //Then
        assertEquals(2, validatedTrelloBoards.size());
        assertEquals("Ricky Wubbalubbadubdub", validatedTrelloBoards.get(0).getName());
        assertEquals(0, validatedEmptyTrelloBoards.size());
    }

    @Test
    public void validateCard() {
        //Given
        TrelloCard cardOne = new TrelloCard("Morty_0", "Ricky Wubbalubbadubdub", "posl", "idl");
        //TrelloCard cardTwp = new TrelloCard("Morty_1", "Means I am in great pain, please help me", "posl", "idl");

        //When
        trelloValidator.validateCard(cardOne);
        //trelloValidator.validateCard(null);

        //Then
        assertEquals("Morty_0", cardOne.getName());

    }
}