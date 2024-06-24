package Model;

import javafx.scene.Node;

import java.util.concurrent.atomic.AtomicReference;

public class SpecialAction {
    public static void bitingFrost(){

    }

    public static void impenetrableFog(){

    }

    public static void torrentialRain(){

    }

    public static void decoy(Card decoy) {
        for (Card card1 : User.getTurnUser().getBoard().getCloseCombat()) {
            AtomicReference<Card> card = new AtomicReference<>(card1);
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
        for (Card card1 : User.getTurnUser().getBoard().getSiege()) {
            AtomicReference<Card> card = new AtomicReference<>(card1);
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
        for (Card card1 : User.getTurnUser().getBoard().getRanged()) {
            AtomicReference<Card> card = new AtomicReference<>(card1);
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
    }



    public static void Villentretenmerth(){

    }

    public static void toad(){

    }

    public static void schirru(){

    }

    public static void mardroeme() {

    }

    public static void berserker() {

    }


    public static void cerys(){

    }

    public static void kombi(){

    }

    public static void shieldmaide(){

    }

    public static void clanDimunPirate(){

    }
    public static void youngBerserker() {

    }
}
