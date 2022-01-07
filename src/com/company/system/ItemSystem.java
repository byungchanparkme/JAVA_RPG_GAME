package com.company.system;

import com.company.item.Item;
import com.company.item.OrderSheet;
import com.company.item.Potion;
import com.company.types.ItemTypes;
import com.company.types.MonsterTypes;
import com.company.utils.TableFormatter;

import java.util.ArrayList;

public class ItemSystem {
    public static Item create(String name) {
        if (name == ItemTypes.RED_POTION.name()) {
            return new Potion("빨간 포션", "RED");
        }
        else if (name == ItemTypes.ORANGE_POTION.name()) {
            return new Potion("주황 포션", "ORANGE");
        }
        else if (name == ItemTypes.WHITE_POTION.name()) {
            return new Potion("하얀 포션", "WHITE");
        }
        else if (name == ItemTypes.ELIXIR.name()) {
            return new Potion("엘릭서", "PURPLE");
        }
        else if (name == ItemTypes.RED_ORDER_SHEET.name()) {
            return new OrderSheet("빨강 주문서", "RED");
        }
        else if (name == ItemTypes.ORANGE_ORDER_SHEET.name()) {
            return new OrderSheet("주황 주문서", "ORANGE");
        }
        else if (name == ItemTypes.WHITE_ORDER_SHEET.name()) {
            return new OrderSheet("하얀 주문서", "WHITE");
        }
        else {
            System.out.println("잘못된 아이템입니다.");
            return null;
        }
    }
    public static ArrayList<Item> createMonsterDropItemList(String monsterName) {
        ArrayList<Item> dropItemList = new ArrayList<Item>();
        // 몬스터 별로 드랍 아이템 목록이 달라짐.
        if (monsterName == MonsterTypes.GREEN_SNAIL.name()) {
            // 초록 달팽이 : 빨강 포션, 빨강 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.BLUE_SNAIL.name()) {
            // 파란 달팽이 : 빨강 포션, 빨강 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.RED_SNAIL.name()) {
            // 빨간 달팽이 : 빨강 포션, 빨강 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.PIG.name()) {
            // 빨간 달팽이 : 빨강 포션, 빨강 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.SLIME.name()) {
            // 슬라임 : 빨강 포션, 빨강 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.STUMP.name()) {
            // 스텀프 : 빨강 포션, 오렌지 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.RIBBON_PIG.name()) {
            // 리본돼지 : 빨강 포션, 오렌지 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.CYNICAL_ORANGE_MUSHROOM.name()) {
            // 시니컬한 주황 버섯 : 빨강 포션, 오렌지 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.STRANGE_PIG.name()) {
            // 이상한 돼지 : 빨강 포션, 빨강 주문서, 오렌지 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.RED_ORDER_SHEET.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.GREEN_MUSHROOM.name()) {
            // 초록 버섯 : 빨강 포션, 오렌지 주문서
            dropItemList.add(create(ItemTypes.RED_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.HORN_MUSHROOM.name()) {
            // 뿔버섯 : 오렌지 포션, 하얀 포션
            dropItemList.add(create(ItemTypes.ORANGE_POTION.name()));
            dropItemList.add(create(ItemTypes.WHITE_POTION.name()));
        }
        else if (monsterName == MonsterTypes.LACE.name()) {
            // 레이스 : 하얀 포션, 엘릭서
            dropItemList.add(create(ItemTypes.WHITE_POTION.name()));
            dropItemList.add(create(ItemTypes.ELIXIR.name()));
        }
        else if (monsterName == MonsterTypes.BLUE_MUSHROOM.name()) {
            // 파란 버섯 : 오렌지 포션, 하얀 주문서
            dropItemList.add(create(ItemTypes.ORANGE_POTION.name()));
            dropItemList.add(create(ItemTypes.WHITE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.ZOMBIE_MUSHROOM.name()) {
            // 좀비 버섯 : 오렌지 포션, 흰색 포션, 오렌지 주문서, 하얀 주문서
            dropItemList.add(create(ItemTypes.ORANGE_POTION.name()));
            dropItemList.add(create(ItemTypes.WHITE_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
            dropItemList.add(create(ItemTypes.WHITE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.EVIL_EYE.name()) {
            // 이블아이 : 오렌지 포션, 하얀 포션, 오렌지 주문서, 하얀 주문서
            dropItemList.add(create(ItemTypes.ORANGE_POTION.name()));
            dropItemList.add(create(ItemTypes.WHITE_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
            dropItemList.add(create(ItemTypes.WHITE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.WILD_BOER.name()) {
            // 와일드보어 : 오렌지 포션, 오렌지 주문서
            dropItemList.add(create(ItemTypes.ORANGE_POTION.name()));
            dropItemList.add(create(ItemTypes.ORANGE_ORDER_SHEET.name()));
        }
        else if (monsterName == MonsterTypes.MUSHMOM.name()) {
            dropItemList.add(create(ItemTypes.WHITE_POTION.name()));
            dropItemList.add(create(ItemTypes.ELIXIR.name()));
            dropItemList.add(create(ItemTypes.WHITE_ORDER_SHEET.name()));
        }
        return dropItemList;
    }
    public static void printItemInfo() {
        // 포션 아이템 정보 및 효과 출력
        // 아이템명    영어로    아이템 효과
        TableFormatter tableFormatter = new TableFormatter(3);

        System.out.println("========================== 아이템 도감 ==========================");
        tableFormatter.insert("아이템명","영어로","아이템 효과");
        tableFormatter.insert("빨간포션","RED_POTION", "HP 50 회복");
        tableFormatter.insert("주황포션", "ORANGE_POTION", "HP 150 회복");
        tableFormatter.insert("하얀포션", "WHITE_POTION", "HP 300 회복");
        tableFormatter.insert("엘릭서", "ELIXIR", " HP 50% 회복");
        tableFormatter.insert("빨간주문서","RED_ORDER_SHEET", "3턴 간 공격력 10% 증가");
        tableFormatter.insert("주황주문서", "ORANGE_ORDER_SHEET", "3턴 간 공격력 20% 증가");
        tableFormatter.insert("하얀주문서","WHITE_ORDER_SHEET", "3턴 간 공격력 50% 증가");
        System.out.println(tableFormatter + "\n");
    }
}
