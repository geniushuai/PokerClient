package com.game.model.fightlandlord;

import com.game.model.plaything.PokerColor;
import com.game.model.plaything.PokerValue;
import com.game.pokerclient.model.Game;
import com.game.pokerclient.model.Player;

/**
 * Players: P1, P2, P3
 * 
 * 游戏的规则需要在客户端实现
 */
public class FightLandlordGame extends Game<FightLandlordGameSetting> {

	/**
	 * 玩家个数
	 */
	public static final int PLAYER_COGAME_NUMBER = 3;

	/** 游戏积分倍数 */
	private int multiple = 1;

	/**
	 * 首发扑克：红桃3
	 */
	public static final FightLandlordPoker START_POKER = new FightLandlordPoker(
			PokerColor.HEART, PokerValue.V3);
	/**
	 * 首发扑克：方块3[候选]
	 */
	public static final FightLandlordPoker START_POKER_DIAMOND = new FightLandlordPoker(
			PokerColor.DIAMOND, PokerValue.V3);
	/**
	 * 首发扑克：黑桃3[候选]
	 */
	public static final FightLandlordPoker START_POKER_SPADE = new FightLandlordPoker(
			PokerColor.SPADE, PokerValue.V3);
	/**
	 * 首发扑克：梅花3[候选]
	 */
	public static final FightLandlordPoker START_POKER_CLUB = new FightLandlordPoker(
			PokerColor.CLUB, PokerValue.V3);

	public FightLandlordGame() {
	}

	/**
	 * @return the multiple
	 */
	public int getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(int multiple) {
		multiple = this.multiple;
	}

	/**
	 * 翻倍积分
	 */
	public void addMultiple() {
		multiple *= 2;
	}

	/* (non-Javadoc)
	 * @see com.game.pokerclient.model.Game#persistScore()
	 */
	@Override
	public void persistScore() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.game.pokerclient.model.Game#persistDisconnectScore(com.game.pokerclient.model.Player)
	 */
	@Override
	public void persistDisconnectScore(Player disconnectedPlayer) {
		// TODO Auto-generated method stub
		
	}

}
