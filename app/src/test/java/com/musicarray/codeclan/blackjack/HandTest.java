package com.musicarray.codeclan.blackjack;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

/**
 * Created by user on 12/28/17.
 */

public class HandTest {

    Hand hand;
    Deck deck1;
    Card card;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    Card card7;
    Card card8;
    Card card9;
    Card card10;
    Card card11;
    Card card12;
    Card card13;
    Card card14;
    Card card15;
    Card card16;
    Card card17;

    @Before
    public void before(){
        hand = new Hand();
        deck1 = new Deck();
        deck1.populateDeck();
        card = new Card(CardValue.JACK, SuitType.SPADES);
        card2 = new Card(CardValue.ACE, SuitType.CLUBS);
        card3 = new Card(CardValue.QUEEN, SuitType.CLUBS);
        card4 = new Card(CardValue.QUEEN, SuitType.DIAMONDS);
        card5 = new Card(CardValue.ACE, SuitType.DIAMONDS);
        card6 = new Card(CardValue.ACE, SuitType.SPADES);
        card7 = new Card(CardValue.ACE, SuitType.HEARTS);
        card8 = new Card(CardValue.TWO, SuitType.DIAMONDS);
        card9 = new Card(CardValue.THREE, SuitType.DIAMONDS);
        card10 = new Card(CardValue.FOUR, SuitType.DIAMONDS);
        card11 = new Card(CardValue.FIVE, SuitType.DIAMONDS);
        card12 = new Card(CardValue.SIX, SuitType.DIAMONDS);
        card13 = new Card(CardValue.TEN, SuitType.DIAMONDS);
        card14 = new Card(CardValue.JACK, SuitType.DIAMONDS);
        card15 = new Card(CardValue.QUEEN, SuitType.DIAMONDS);
        card16 = new Card(CardValue.KING, SuitType.DIAMONDS);
        card17 = new Card(CardValue.ACE, SuitType.DIAMONDS);


    }

    @Test
    public void canGetHandSize(){
        assertEquals(0, hand.getHandSize());
    }

    @Test
    public void canGetTopCard(){
        deck1.deal(hand);
        assertEquals("The Ace of Hearts", hand.getCardsHeld().get(0).prettyName());
    }

    @Test
    public void canGetHandValue(){
        deck1.deal(hand);
        deck1.deal(hand);
        assertEquals(13, hand.getHandValue());
    }

    @Test
    public void canSeeHand(){
        deck1.deal(hand);
        deck1.deal(hand);
        assertEquals("The Ace of Hearts\n" +
                "The Two of Hearts\n", hand.viewCards());
    }

    @Test
    public void canHideCards(){
        deck1.deal(hand);
        deck1.deal(hand);
        deck1.deal(hand);
        assertEquals("Secret\n" +
                "The Two of Hearts\n" +
                "The Three of Hearts\n", hand.viewComputerCards());
    }

    @Test
    public void canCheckForAce_True(){
        deck1.deal(hand);
        deck1.deal(hand);
        deck1.deal(hand);
        hand.addCards(card2);
        assertEquals(true, hand.checkAces());
    }

    @Test
    public void canCheckForAce_False(){
        hand.addCards(card);
        hand.addCards(card3);
        assertEquals(false, hand.checkAces());
    }

    @Test
    public void canGetAceToReturnAs11(){
        hand.addCards(card);
        hand.addCards(card2);
        assertEquals(21, hand.getHandValue());
    }

    @Test
    public void canGetAceToReturnAs1(){
        hand.addCards(card);
        hand.addCards(card2);
        hand.addCards(card3);
        assertEquals(21, hand.getHandValue());
    }

    @Test public void canCheckDuplicateCards() {
        hand.addCards(card);
        hand.addCards(card2);
        hand.addCards(card3);
        hand.addCards(card4);
        hand.addCards(card5);
        HashMap<CardValue, Integer> result = new HashMap<>();
        result.put(CardValue.JACK, 1);
        result.put(CardValue.ACE, 2);
        result.put(CardValue.QUEEN, 2);
        assertEquals(result, hand.checkDuplicateCardValues());
    }

    @Test public void canGetFourOfAKind() {
        hand.addCards(card);
        hand.addCards(card2);
        hand.addCards(card5);
        hand.addCards(card6);
        hand.addCards(card7);
        assertEquals(8, hand.checkHand());

    }

    @Test public void canGetFullHouse() {
        hand.addCards(card3);
        hand.addCards(card4);
        hand.addCards(card5);
        hand.addCards(card6);
        hand.addCards(card7);
        assertEquals(7, hand.checkHand());

    }

    @Test public void canGetThreeOfAKind() {
        hand.addCards(card);
        hand.addCards(card3);
        hand.addCards(card5);
        hand.addCards(card6);
        hand.addCards(card7);
        assertEquals(4, hand.checkHand());

    }

    @Test public void canGetTwoPairs(){
        hand.addCards(card6);
        hand.addCards(card7);
    }

    @Test public void canGetTwoOfAKind() {
        hand.addCards(card3);
        hand.addCards(card4);
        hand.addCards(card6);
        hand.addCards(card7);
        assertEquals(3, hand.checkHand());

    }

    @Test public void canOrderHand_1(){
        hand.addCards(card);
        hand.addCards(card2);
        hand.addCards(card5);
        hand.addCards(card6);
        hand.addCards(card7);
        HashMap<CardValue, Integer> result = new HashMap<>();
        result.put(CardValue.ACE, 4);
        result.put(CardValue.JACK, 1);
        assertEquals(result, hand.checkDuplicateCardValues());
    }

    @Test public void canOrderHand_2(){
        hand.addCards(card3);
        hand.addCards(card4);
        hand.addCards(card5);
        hand.addCards(card6);
        hand.addCards(card7);
        HashMap<CardValue, Integer> result = new HashMap<>();
        result.put(CardValue.ACE, 3);
        result.put(CardValue.QUEEN, 2);
        assertEquals(result, hand.checkDuplicateCardValues());
    }

    @Test public void checkFlush_True(){
        hand.addCards(card4);
        hand.addCards(card5);
        hand.addCards(card8);
        hand.addCards(card9);
        hand.addCards(card10);
        assertEquals(true, hand.checkFlush());
        assertEquals(6, hand.checkHand());

    }

    @Test public void checkFlush_False(){
        hand.addCards(card4);
        hand.addCards(card5);
        hand.addCards(card8);
        hand.addCards(card3);
        hand.addCards(card);
        assertEquals(false, hand.checkFlush());
        assertEquals(2, hand.checkHand());

    }

    @Test public void canCheckForStraight_True(){
        hand.addCards(card8);
        hand.addCards(card9);
        hand.addCards(card10);
        hand.addCards(card11);
        hand.addCards(card12);
        assertEquals(true, hand.checkStraight());
        assertEquals(9, hand.checkHand());

    }

    @Test public void canCheckForStraight_False(){
        hand.addCards(card);
        hand.addCards(card9);
        hand.addCards(card10);
        hand.addCards(card11);
        hand.addCards(card12);
        assertEquals(false, hand.checkStraight());
        assertEquals(1, hand.checkHand());

    }

    @Test public void canCheckForRoyalFlush_True(){
        hand.addCards(card13);
        hand.addCards(card14);
        hand.addCards(card15);
        hand.addCards(card16);
        hand.addCards(card17);
        assertEquals(true, hand.checkRoyalFlush());
        assertEquals(10, hand.checkHand());
    }

    @Test public void canCheckForRoyalFlush_False(){
        hand.addCards(card);
        hand.addCards(card14);
        hand.addCards(card15);
        hand.addCards(card16);
        hand.addCards(card17);
        assertEquals(false, hand.checkRoyalFlush());
        assertEquals(2, hand.checkHand());
    }




}
