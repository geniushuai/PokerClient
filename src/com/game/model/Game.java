package com.game.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 
 *
 * @param <T>
 */
public abstract class Game<T> {

//    protected Log log = LogFactory.getLog(Game.class);
//
//    /** 游戏玩家 */
//    private List<Player> players = Collections.synchronizedList(new ArrayList<Player>());
//
//    /** 游戏玩家与玩家编号映射关系 */
//    private Map<String, Player> playerNumberMap = Collections.synchronizedMap(new HashMap<String, Player>());

    /** 游戏记录 */
    private StringBuilder gameRecord = new StringBuilder();

    /** 游戏ID */
    private final String id = UUID.randomUUID().toString();

    /** 获胜者名单 */
    private StringBuilder winnerNumbers = new StringBuilder();

    /** 当前房间每局游戏所需要的分数 */
    private double gameMark;
    
    /** 扑克类专用 【红五 独牌】或【斗地主 青龙】记分 */
    private double lowLevelMark;
    
    /** 扑克类专用 【红五 天独】或【斗地主 白虎】记分 */
    private double midLevelMark;
    
    /** 扑克类专用 【红五 天外天】或【斗地主 朱雀】记分 */
    private double highLevelMark;

    /** 游戏进行所需要的最少底分 */
    private double minGameStartMark;

    /** 游戏设置 */
    private T setting; 

    /** 自定义结构，用于表现游戏积分明细 */
    private String gameDetailScore;

    /**
     * 默认系统分
     */
    private static int systemScoreRate = 0;

    /** 游戏创建时间 */
    private Date createTime = new Date();

    /**
     * @return
     */
    public T getSetting() {
        return this.setting;
    }

    /**
     * @param setting
     */
    public void setSetting(T setting) {
        this.setting = setting;
    }

//    /**
//     * @param player
//     */
//    public void involvePlayer(Player player) {
//        players.add(player);
//    }
//
//    /**
//     * @return the players
//     */
//    public List<Player> getPlayers() {
//        return players;
//    }

    /**
     * @return the gameRecord
     */
    public String getGameRecord() {
        return gameRecord.toString();
    }

    /**
     * @param gameRecord
     *            the gameRecord to set
     */
    public void appendGameRecord(String gameRecord) {
        this.gameRecord.append(gameRecord).append(";");
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param winner
     * @return
     */
    public String getWinnerNumbers() {
        return winnerNumbers.toString().replaceFirst("~$", "");
    }

    /**
     * @param winner
     * @param playerNumber
     */
    public void addWinnerNumber(String playerNumber) {
        if (winnerNumbers.indexOf(playerNumber) > -1) {
            throw new RuntimeException("该玩家已经在获胜者名单中！");
        }
        winnerNumbers.append(playerNumber).append("~");
    }

    /**
     * @return the gameMark
     */
    public double getGameMark() {
        return gameMark;
    }

    /**
     * @param gameMark the gameMark to set
     */
    public void setGameMark(double gameMark) {
        this.gameMark = gameMark;
    }

    /**
	 * @return the lowLevelMark
	 */
	public double getLowLevelMark() {
		return lowLevelMark;
	}

	/**
	 * @param lowLevelMark the lowLevelMark to set
	 */
	public void setLowLevelMark(double lowLevelMark) {
		this.lowLevelMark = lowLevelMark;
	}

	/**
	 * @return the midLevelMark
	 */
	public double getMidLevelMark() {
		return midLevelMark;
	}

	/**
	 * @param midLevelMark the midLevelMark to set
	 */
	public void setMidLevelMark(double midLevelMark) {
		this.midLevelMark = midLevelMark;
	}

	/**
	 * @return the highLevelMark
	 */
	public double getHighLevelMark() {
		return highLevelMark;
	}

	/**
	 * @param highLevelMark the highLevelMark to set
	 */
	public void setHighLevelMark(double highLevelMark) {
		this.highLevelMark = highLevelMark;
	}

	/**
     * @return the minGameStartMark
     */
    public double getMinGameStartMark() {
        return minGameStartMark;
    }

    /**
     * @param minGameStartMark the minGameStartMark to set
     */
    public void setMinGameStartMark(int minGameStartMark) {
        this.minGameStartMark = minGameStartMark;
    }

//    /**
//     * @return the playerNumberMap
//     */
//    public Map<String, Player> getPlayerNumberMap() {
//        return playerNumberMap;
//    }



    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

}
