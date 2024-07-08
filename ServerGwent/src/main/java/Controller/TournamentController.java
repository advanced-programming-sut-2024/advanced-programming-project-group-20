package Controller;

import Model.Tournament;
import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;

import java.io.IOException;
import java.util.ArrayList;

public class TournamentController {
    public static SendingPacket register(ArrayList<Object> objects) throws IOException, InterruptedException {
        String username = (String) objects.get(0);
        if (Tournament.getTournament() == null ) Tournament.setTournament(new Tournament());
        Tournament.getTournament().addPlayer(username);
        sendNewTableForAllConnections();
        return null;
    }

    private static void sendNewTableForAllConnections() throws IOException, InterruptedException {

        Connection connection;
        Object[] objects = new Object[1];
        if (Tournament.getTournament().getChampion() != null){
            Tournament.getTournaments().add(Tournament.getTournament());
            Tournament.setTournament(new Tournament());
        }
        objects[0] = Tournament.getTournament();
        for (User user : User.getAllUsers()) {
            connection = Connection.getConnectionByUserName(user.getUsername());
            if (connection == null) continue;
            connection.sendOrder(new SendingPacket("TournamentMenu","updateTournament",objects));
        }
    }


    public static SendingPacket gameSender(ArrayList<Object> objects) throws IOException {
        User user = User.getUserByName((String) objects.get(0));
        Tournament tournament = Tournament.getTournament();
        int pot = potYob(user.getUsername());
        int oppPot;
        if (pot % 2 == 0) {
            oppPot = pot + 1;
        } else {
            oppPot = pot - 1;
        }
        Object[] objects1 = new Object[1];
        objects1[0] = Tournament.getTournament();
        if (tournament.getTable()[oppPot] == null) return new SendingPacket("TournamentMenu","notHaveOpp",objects1);
        else {
            Connection connection = Connection.getConnectionByUserName(tournament.getTable()[oppPot]);
            if (connection == null) return new SendingPacket("TournamentMenu","offlineOpp",objects1);
            else {
                connection.sendOrder(new SendingPacket("TournamentMenu","gameRequest",objects1));
            }
        }
        return null;
    }

    public static SendingPacket giveUp(ArrayList<Object> objects) throws IOException, InterruptedException {
        String username = (String) objects.get(0);
        Tournament tournament = Tournament.getTournament();
        int pot = potYob(username);
        int oppPot;
        if (pot % 2 == 0) {
            oppPot = pot + 1;
        } else {
            oppPot = pot - 1;
        }
        updateTable(tournament.getTable()[oppPot],username);
        sendNewTableForAllConnections();
        return null;
    }

    public static SendingPacket acceptGame(ArrayList<Object> objects) throws IOException, InterruptedException {
        User user = User.getUserByName((String) objects.get(0));
        int pot = potYob(user.getUsername());
        int oppPot;
        if (pot % 2 == 0) {
            oppPot = pot + 1;
        } else {
            oppPot = pot - 1;
        }
        User opp = User.getUserByName(Tournament.getTournament().getTable()[oppPot]);
        user.setOppName(opp.getUsername());
        opp.setOppName(user.getUsername());
        Tournament.getTournament().getActiveGames().add(user.getUsername() + " " + opp.getUsername());
        sendNewTableForAllConnections();
        Connection connection = Connection.getConnectionByUserName(opp.getUsername());
        Object[] objects1 = new Object[1];
        objects1[0] = user.getUsername();
        connection.sendOrder(new SendingPacket("MainMenu", "goToPreGame", objects1));
        objects1[0] = opp.getUsername();
        return new SendingPacket("MainMenu", "goToPreGame", objects1);
    }

    public static void setResult(String user, String user1) throws IOException, InterruptedException {
        Tournament tournament = Tournament.getTournament();
        for (String string : tournament.getActiveGames()) {
            if (string.contains(user) && string.contains(user1)) {
                updateTable(user, user1);
                tournament.getActiveGames().remove(string);
                break;
            }
        }
        sendNewTableForAllConnections();

    }

    private static void updateTable(String winner, String loser) {
        int winnerPot = potYob(winner);
        int loserPot;
        if (winnerPot % 2 == 0) {
            loserPot = winnerPot + 1;
        } else {
            loserPot = winnerPot - 1;
        }
        newLoserPot(loserPot, loser);
        newWinenrPot(winnerPot, winner);
    }

    private static void newWinenrPot(int winnerPot, String winner) {
        switch (winnerPot) {
            case 0, 1:
                Tournament.getTournament().getTable()[8] = winner;
                break;
            case 2, 3:
                Tournament.getTournament().getTable()[9] = winner;
                break;
            case 4, 5:
                Tournament.getTournament().getTable()[10] = winner;
                break;
            case 6, 7:
                Tournament.getTournament().getTable()[11] = winner;
                break;
            case 8, 9:
                Tournament.getTournament().getTable()[22] = winner;
                break;
            case 10, 11:
                Tournament.getTournament().getTable()[23] = winner;
                break;
            case 12, 13:
                Tournament.getTournament().getTable()[16] = winner;
                break;
            case 14, 15:
                Tournament.getTournament().getTable()[17] = winner;
                break;
            case 16, 17:
                Tournament.getTournament().getTable()[20] = winner;
                break;
            case 18, 19:
                Tournament.getTournament().getTable()[21] = winner;
                break;
            case 20, 21:
                Tournament.getTournament().getTable()[25] = winner;
                break;
            case 22, 23:
                Tournament.getTournament().getTable()[26] = winner;
                break;
            case 24, 25:
                Tournament.getTournament().getTable()[27] = winner;
                break;
            case 26, 27:
                Tournament.getTournament().setChampion(winner);
                break;
        }
    }

    private static void newLoserPot(int loserPot, String loser) {
        switch (loserPot) {
            case 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 24, 25, 26, 27:
                Tournament.getTournament().getNames().remove(loser);
                break;
            case 0, 1:
                Tournament.getTournament().getTable()[12] = loser;
                break;
            case 2, 3:
                Tournament.getTournament().getTable()[13] = loser;
                break;
            case 4, 5:
                Tournament.getTournament().getTable()[14] = loser;
                break;
            case 6, 7:
                Tournament.getTournament().getTable()[15] = loser;
                break;
            case 8, 9:
                Tournament.getTournament().getTable()[18] = loser;
                break;
            case 10, 11:
                Tournament.getTournament().getTable()[19] = loser;
                break;
            case 22, 23:
                Tournament.getTournament().getTable()[24] = loser;
                break;
        }
    }

    private static int potYob(String user) {
        String[] strings = Tournament.getTournament().getTable();
        for (int i = 27; i >= 0; i--) {
            if (strings[i] == null) continue;
            else if (strings[i].equals(user)) return i;
        }
        return 0;
    }

    public static SendingPacket getCurrentTable(ArrayList<Object> objects) {
        Object[] objects1 = new Object[1];
        if (Tournament.getTournament().getChampion() != null){
            Tournament.getTournaments().add(Tournament.getTournament());
            Tournament.setTournament(new Tournament());
        }
        objects1[0] = Tournament.getTournament();
        return new SendingPacket("TournamentMenu","updateTournament",objects1);
    }

    public static SendingPacket getCurrentGames(ArrayList<Object> objects) {
        ArrayList<String> games = new ArrayList<>();
        for (String string : Tournament.getTournament().getActiveGames()) {
            games.add(string);
        }
        Object[] objects1 = new Object[1];
        objects1[0] = games;
        return new SendingPacket("TournamentMenu","showGamesList",objects1);
    }

    public static SendingPacket startTournamentMenu(ArrayList<Object> objects) {
        Object[] objects1 = new Object[1];
        objects1[0] = Tournament.getTournament();
        return new SendingPacket("MainMenu","goToTournament",objects1);
    }

    public static SendingPacket seeGame(ArrayList<Object> objects) throws Exception {
        User user1 = User.getUserByName((String) objects.get(0));
        User user2 = User.getUserByName((String) objects.get(1));
        Object[] objects1 = new Object[2];
        objects1[0] = user1;
        objects1[1] = user2;
        return new SendingPacket("MainMenu", "showGame", objects1);
    }
}
