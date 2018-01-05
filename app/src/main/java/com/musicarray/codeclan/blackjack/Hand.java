package com.musicarray.codeclan.blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 12/28/17.
 */

public class Hand implements Serializable {

    private ArrayList<Card> cardsHeld;
    private String pokerWinMessage;
    private int highestPokerCard;


    public Hand() {
        this.cardsHeld = new ArrayList<>();
        this.pokerWinMessage = "";
        this.highestPokerCard = 0;
    }

    public int getHighestPokerCard() {
        return highestPokerCard;
    }

    public void setHighestPokerCard(int highestPokerCard) {
        this.highestPokerCard = highestPokerCard;
    }

    public String getPokerWinMessage() {
        return pokerWinMessage;
    }

    public void setPokerWinMessage(String pokerWinMessage) {
        this.pokerWinMessage = pokerWinMessage;
    }

    public ArrayList<Card> getCardsHeld() {
        return cardsHeld;
    }

    public void addCards(Card card){
        cardsHeld.add(card);
    }

    public int getHandSize(){
        return cardsHeld.size();
    }

    public void clearHand(){
        cardsHeld.clear();
    }

    public int getHandValue() {
        int lowScore = 0;
        int highScore = 0;
        int score;
            for(Card card : cardsHeld){
                lowScore += card.getCardValue().getValue();
                highScore += card.getCardValue().getValue();
        }
        highScore += 10;
        if (checkAces() == true && highScore <= 21){
                score = highScore;
        }
        else {
            score = lowScore;
        }
        return score;
    }

    public boolean checkAces(){
        for (Card card : cardsHeld){
            if (card.getCardValue() == CardValue.ACE){
                return true;
            }
        }
        return false;
    }

    public String viewCards() {
        ArrayList<String> cards = new ArrayList<>();
        for (Card card : cardsHeld){
            cards.add(card.prettyName());
        }
        StringBuilder result = new StringBuilder();
        for (String s : cards)
        {
            result.append(s);
            result.append("\n");
        }
        return result.toString();
    }

    public String viewComputerCards() {
        ArrayList<String> cards = new ArrayList<>();
        for (Card card : cardsHeld) {
            if (card == cardsHeld.get(0)) {
                cards.add(card.hideCard());
            } else if (card != cardsHeld.get(0)) {
                cards.add(card.prettyName());
            }
        }
        StringBuilder result = new StringBuilder();
        for (String s : cards)
        {
            result.append(s);
            result.append("\n");
        }
            return result.toString();
        }

    public ArrayList<String> pictureIDs() {
        ArrayList<String> cards = new ArrayList<>();
        for (Card card : cardsHeld){
            cards.add(card.getCardPicture());
        }
       return cards;
    }

    public HashMap<CardValue, Integer> checkDuplicateCardValues(){
        HashMap<CardValue, Integer> cardValueCount = new HashMap<>();
            for (Card card : cardsHeld){
                if (cardValueCount.containsKey(card.getCardValue())){
                    int count = Integer.parseInt(String.valueOf(cardValueCount.get(card.getCardValue())));
                    cardValueCount.put(card.getCardValue(), count + 1);
                }
                else {
                    cardValueCount.put(card.getCardValue(), 1);
                }
            }
            Map<CardValue, Integer> result = new HashMap<>(sortValues(cardValueCount));
            Set<CardValue> keys = result.keySet();
            ArrayList<Integer> highestCards = new ArrayList<>();
                for (CardValue cardValue : keys){
                    highestCards.add(cardValue.getPokerValue());
                }
            setHighestPokerCard(highestCards.get(0));
            return sortValues(cardValueCount);
    }

    public HashMap<CardValue, Integer> sortValues(HashMap<CardValue, Integer> map){
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<CardValue, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<CardValue, Integer>) o1).getValue());
            }
        });
        HashMap<CardValue, Integer> resultHash = new HashMap<>();
        for (Object e : a) {
            resultHash.put(((Map.Entry<CardValue, Integer>) e).getKey(),((Map.Entry<CardValue, Integer>) e).getValue());
        }
        return resultHash;
    }



    public int checkHand() {
        int result = 1;
        HashMap<CardValue, Integer> cardValueCount = new HashMap<>(checkDuplicateCardValues());
        ArrayList<Integer> numberOfTwos = new ArrayList<>();
        ArrayList<Integer> checkPairs = new ArrayList<>(cardValueCount.values());
        setPokerWinMessage("a High Card");
            if (checkRoyalFlush() == true){
                setPokerWinMessage("a Royal Flush");
                result = 10;
            }
            else if (checkFlush() == true && checkStraight() == true){
                setPokerWinMessage("a Straight Flush");
                result = 9;
            }
             else if (checkPairs.contains(4)) {
                setPokerWinMessage("Four of a Kind");
                result = 8;
            }
            else if (checkPairs.contains(3) && checkPairs.contains(2)) {
                setPokerWinMessage("a Full House");
                result = 7;
            }
            else if (checkFlush() == true) {
                setPokerWinMessage("a Flush");
                result = 6;
            }
            else if(checkStraight() == true){
                setPokerWinMessage("a Straight");
                result = 5;
            }
            else if (checkPairs.contains(3)) {
                setPokerWinMessage("Three of a Kind");
                result = 4;
            }
            else if (checkPairs.contains(2)) {
                for(Integer cardValue : checkPairs){
                    if (cardValue == 2){
                        numberOfTwos.add(cardValue);
                    }

                }
                if (numberOfTwos.size() == 2){
                    setPokerWinMessage("Two Pairs");
                    result = 3;
                }
                else {
                    result = 2;
                setPokerWinMessage("One Pair");
                }

            }
           return result;
    }

    public boolean checkFlush(){
        ArrayList<Card> resultArray = new ArrayList<>();
        ArrayList<Integer> checkHighestCard = new ArrayList<>();
        Card checkCard = cardsHeld.get(0);
        for (Card card : cardsHeld){
            if (checkCard.getSuitType() == card.getSuitType()){
                resultArray.add(card);
                checkHighestCard.add(card.getPokerValue());
            }
            Collections.sort(checkHighestCard);
            setHighestPokerCard(checkHighestCard.get(checkHighestCard.size() -1));
        }
        if (resultArray.size() == 5){
            return true;
        }
        return false;
    }

    public boolean checkStraight(){
        ArrayList<Integer> cardValues = new ArrayList<>();
        for (Card card : cardsHeld){
            cardValues.add(card.getPokerValue());
        }
        Collections.sort(cardValues);
        setHighestPokerCard(cardValues.get(cardValues.size() -1));
        ArrayList<Integer> resultArray = new ArrayList<>();
        int firstCardValue = cardValues.remove(0);
        for (int cardValue : cardValues){
            if(cardValue == firstCardValue + 1){
                firstCardValue = cardValue;
                resultArray.add(cardValue);
            }
        }
        if (resultArray.size() == 4){
            return true;
        }
        return false;
    }

    public boolean checkRoyalFlush(){
        ArrayList<Card> resultArray = new ArrayList<>();
        for (Card card : cardsHeld) {
            if (card.getCardValue() == CardValue.TEN || card.getCardValue() == CardValue.JACK ||
                    card.getCardValue() == CardValue.QUEEN || card.getCardValue() == CardValue.KING ||
                    card.getCardValue() == CardValue.ACE){
                resultArray.add(card);

            }
        }
        if (checkFlush() == true && resultArray.size() == 5){
            return true;
        }
        return false;
    }

}
