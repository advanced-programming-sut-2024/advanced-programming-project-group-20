package Model;

import java.util.Random;

public class AbilityActions {
    public static void switchAction(String abilityName,Card card){
        switch (abilityName){
            case "spy":
                spy();
                break;
            case "scorch":
                scorch();
        }

    }
    public static void commanderHorn() {

    }

    public static void medic() {

    }

    public static void moralBoost() {

    }

    public static void muster() {

    }

    public static void spy() {
        Random random = new Random();
        int num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
         num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
    }

    public static void tightBond() {

    }

    public static void scorch() {

    }

    public static void berserker() {

    }

    public static void mardroeme() {

    }

    public static void transformers() {

    }
}
