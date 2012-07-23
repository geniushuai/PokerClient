package com.game.model.fightlandlord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.game.model.plaything.PokerColor;
import com.game.model.plaything.PokerValue;

/**
 * 斗地主扑克
 */
public class FightLandlordPoker {

	private static final String prioritySequence = "V3,V4,V5,V6,V7,V8,V9,V10,VJ,VQ,VK,VA,V2,VX,VY";
	private PokerColor colorStyle;
	private PokerValue valueStyle;
	private int priorityValue;

	/**
	 * 底牌
	 */
	public static FightLandlordPoker[] handlePokers = new FightLandlordPoker[3];
	
	public FightLandlordPoker(PokerColor colorStyle, PokerValue valueStyle) {
		this.colorStyle = colorStyle;
		this.valueStyle = valueStyle;
		this.priorityValue = prioritySequence.indexOf(valueStyle.name()) * 10;
	}

	/**
	 * @return the priorityValue
	 */
	public int getPriorityValue() {
		return this.priorityValue;
	}

	/**
	 * @return the colorStyle
	 */
	public PokerColor getColorStyle() {
		return colorStyle;
	}

	/**
	 * @return the valueStyle
	 */
	public PokerValue getValueStyle() {
		return valueStyle;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return colorStyle.ordinal() + valueStyle.name();
	}

	/**
	 * 正常洗牌
	 * 
	 * @return
	 */
	public static FightLandlordPoker[][] shuffle() {
		// 准备洗牌用的扑克
		List<FightLandlordPoker> pokers = new ArrayList<FightLandlordPoker>();
		// 3至大王的索引为0到15
		List<PokerValue> list = Arrays.asList(PokerValue.values()).subList(0,
				15);
		// 准备一副扑克
		for (PokerValue pokerValue : list) {
			if (PokerValue.VX.equals(pokerValue)
					|| PokerValue.VY.equals(pokerValue)) {
				pokers.add(new FightLandlordPoker(PokerColor.NO_COLOR,
						pokerValue));
				continue;
			}
			FightLandlordPoker a = new FightLandlordPoker(PokerColor.HEART,
					pokerValue);
			FightLandlordPoker b = new FightLandlordPoker(PokerColor.DIAMOND,
					pokerValue);
			FightLandlordPoker c = new FightLandlordPoker(PokerColor.CLUB,
					pokerValue);
			FightLandlordPoker d = new FightLandlordPoker(PokerColor.SPADE,
					pokerValue);
			pokers.add(a);
			pokers.add(b);
			pokers.add(c);
			pokers.add(d);
		}
		// 开始随机洗牌
		Collections.shuffle(pokers);
//		// 计算每个玩家手中牌数
//		int eachPokerLength = (pokers.size() % FightLandlordGame.PLAYER_COGAME_NUMBER) == 0 ? (pokers
//				.size() / FightLandlordGame.PLAYER_COGAME_NUMBER)
//				: (pokers.size() / FightLandlordGame.PLAYER_COGAME_NUMBER) + 1;
		
		// 游戏有设置前每个玩家手中牌数
		int eachPokerLength = 17;
		// 开始发牌51张一人三纸[游戏设置前]
		FightLandlordPoker[][] eachShuffledPokers = new FightLandlordPoker[FightLandlordGame.PLAYER_COGAME_NUMBER][eachPokerLength];
		int currentSide = 0;
		for (int i = 0; i < pokers.size() - 3; i += FightLandlordGame.PLAYER_COGAME_NUMBER) {
			for (int j = 0; j < eachShuffledPokers.length; j++) {
				if (i + j == pokers.size()) {
					break;
				}
				eachShuffledPokers[j][currentSide] = pokers.get(i + j);
			}
			currentSide++;
		}
		// 三张底牌
		for (int i = pokers.size() - 3; i < pokers.size(); i += FightLandlordGame.PLAYER_COGAME_NUMBER) {
			for (int j = 0; j < eachShuffledPokers.length; j++) {
				handlePokers[j] = pokers.get(i + j);
			}
		}
		return eachShuffledPokers;
	}

	/**
	 * 按GM需求洗牌
	 * 
	 * @return
	 */
	public static FightLandlordPoker[][] shuffle(String cheatCards) {
		return null;
	}
	
	
	
	public static List<String> sortPokers(String cards)
	{
		List<String> cardArray = Arrays.asList(cards.split(","));
		Comparator<String> cardSort = new Comparator<String>() {
			/**
			 *
			 * @param card1
			 * @param card2
			 * @return
			 *
			 */
			@Override
			public int compare(String card1, String card2)
			{
				if (card1 == card2)
				{
					// 值与花色都相同时
					return 0;
				}
				// 实现排序功能
				int pri1 = prioritySequence.indexOf(card1.replaceAll("^[0-4]", ""));
				int pri2 = prioritySequence.indexOf(card2.replaceAll("^[0-4]", ""));
				// 值比较
				if (pri1 > pri2)
				{
					return 1;
				}
				else if (pri1 < pri2)
				{
					return -1;
				}
				// 值相同时，进行花色比较
				if (card1.charAt(0) > card2.charAt(0))
				{
					return 1;
				}
				else if (card1.charAt(0) < card2.charAt(0))
				{
					return -1;
				}
				return 0;
			}

		};
		Collections.sort(cardArray, cardSort);
		return cardArray;
	}
	
	
	
}
