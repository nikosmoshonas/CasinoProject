package ch.bbbaden.casino.gruppe7.roulette;

import ch.bbbaden.casino.gruppe7.Stats;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bets {

    private List<List<String>> m;
    private int won = 0;
    private int set = 0;
    private int lost = 0;
    Stats stats = new Stats("Roulette");

    Bets(List<List<String>> m) {
        this.m = m;
    }

    public int checkBets(Map<String, Integer> map, int random) throws SQLException {
        won = 0;
        set = 0;
        lost = 0;

        for (Integer value : map.values()) {
            set += value;
        }

        for (int i = 0; i < m.get(random).size(); i++) {
            if (map.containsKey(m.get(random).get(i))) {
                int amount = (int) map.get(m.get(random).get(i));
                won += (amount * multiplicators(i) + amount);
                map.remove(m.get(random).get(i));
            }
        }

        if (won - set < 0) {
            lost = (int) Math.sqrt(Math.pow((won - set), 2));
            stats.setStats(set, 0, lost);
        } else {
            stats.setStats(set, won - set, lost);
        }
        return won;
    }

    public double multiplicators(int index) {
        double factor = 0.0;
        switch (index) {
            case 0:
                factor = 35;
                break;
            case 1:
                factor = 1;
                break;
            case 2:
                factor = 1;
                break;
            case 3:
                factor = 1;
                break;
            case 4:
                factor = 2;
                break;
            case 5:
                factor = 5;
                break;
            case 6:
                factor = 2;
                break;
            case 7:
                factor = 17;
                break;
            case 8:
                factor = 17;
                break;
            case 9:
                factor = 17;
                break;
            case 10:
                factor = 17;
                break;
            case 11:
                factor = 8;
                break;
            case 12:
                factor = 8;
                break;
            case 13:
                factor = 8;
                break;
            case 14:
                factor = 8;
                break;
            case 15:
                factor = 11;
                break;
            case 16:
                factor = 6;
                break;
        }
        return factor;
    }
}
