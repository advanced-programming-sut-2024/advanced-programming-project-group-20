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
        for (Node node : User.getTurnUser().getBoard().getCloseCombat().getChildren()) {
            AtomicReference<Card> card = new AtomicReference<>((Card) node);
            Card card1 = (Card) node;
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
        for (Node node : User.getTurnUser().getBoard().getSiege().getChildren()) {
            AtomicReference<Card> card = new AtomicReference<>((Card) node);
            Card card1 = (Card) node;
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
        for (Node node : User.getTurnUser().getBoard().getRanged().getChildren()) {
            AtomicReference<Card> card = new AtomicReference<>((Card) node);
            Card card1 = (Card) node;
            card.get().setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card1);
                card.set(CardBuilder.neutral("decoy"));
            });
        }
    }

    private static void decoyAction(Card card , Card decoy) {
        card = CardBuilder.neutral("decoy");
        if (card.getFaction().getName().equals("Nilfgaard")) {
            decoy = CardBuilder.nilfgaard(card.getName(), card.getFaction());
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
