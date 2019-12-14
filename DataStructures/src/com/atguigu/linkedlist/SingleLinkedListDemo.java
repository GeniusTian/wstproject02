package com.atguigu.linkedlist;

/**
 * @author wststart
 * @create 2019-06-14 19:02
 */
public class SingleLinkedListDemo {
	public static void main(String[] args) {
//        测试
		HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
		HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
		HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
		HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
		SingleLinkedList sll = new SingleLinkedList();
//        sll.add(hero1);
//        sll.add(hero2);
//        sll.add(hero3);
//        sll.add(hero4);
//        sll.addByOrder(hero1);
//        sll.addByOrder(hero4);
//        sll.addByOrder(hero2);
//        sll.addByOrder(hero2);//无法插入，标号已存在
//        sll.addByOrder(hero3);
		sll.headAdd(hero1);
		sll.headAdd(hero2);
		sll.headAdd(hero3);
		sll.headAdd(hero4);
		sll.linkdeList();
//        SingleLinkedList reverse = SingleLinkedList.reverse(sll);


//        reverse.linkdeList();

	}
}

//定义一个SingleLinkedList来管理我们的节点
class SingleLinkedList {
	//    先初始化一个头结点，头结点不要动，方便遍历以及查找
	private HeroNode head = new HeroNode(0, "", "");

	//    添加节点到单向链表
//    思路，当不考虑编号顺序时
//    1.找到当前链表的最后节点
//    2.将最后这个节点的next指向新的节点
	public void add(HeroNode heroNode) {
		//因为head节点不能动，所以我们需要一个辅助指针temp
		HeroNode temp = head;
//        遍历链表，找到最后
		while (true) {
			if (temp.next == null) {
				break;
			}
//            如果没有找到最后，将指针后移
			temp = temp.next;
		}
//        当退出while循环时，temp就指向了链表的最后
//        将最后这个节点的next指向新的节点
		temp.next = heroNode;
	}

	//    第二种方式添加英雄，根据排名将英雄插入到指定位置
//    如果排名存在就添加失败
	public void addByOrder(HeroNode heroNode) {
		HeroNode temp = head;
//        因为单链表，我们找的temp是位于添加位置的前一个节点，不然无法添加
		boolean flag = false;//标识添加的标号是否存在
		while (true) {
			if (temp.next == null) {//说明已到链表最后
				break;
			}
			if (temp.next.no > heroNode.no) {//位置找到，就在temp的后面插入
				break;
			} else if (temp.next.no == heroNode.no) {//说明希望添加的标号已经存在
				flag = true;
				break;
			}
			temp = temp.next;//指针后移
		}
		if (flag) {
			System.out.println("无法插入，标号已存在");
		} else {
			//插入节点
			heroNode.next = temp.next;
			temp.next = heroNode;
		}
	}

	//    修改节点的信息，根据no编号来修改，编号不能更改
	public void update(HeroNode newHeroNode) {
//        判断链表是否为空
		if (head.next == null) {
			System.out.println("链表为空");
			return;
		}
//        找到需要修改的节点，根据no修改
		HeroNode temp = head.next;
		boolean flag = false;
		while (true) {
			if (temp.no == newHeroNode.no) {
				flag = true;
				break;
			}
			if (temp == null) {
				break;
			}
			temp = temp.next;
		}
		if (flag) {
			temp.setName(newHeroNode.getName());
			temp.setNickname(newHeroNode.getNickname());
		} else {
			System.out.printf("没有找到编号%d的节点，不能修改\n", newHeroNode.no);
		}

	}

	//    显示链表
	public void linkdeList() {
//        判断链表是否为空
		if (head.next == null) {
			System.out.println("链表为空");
			return;
		}
//        因为头结点不能动所以用辅助变量来遍历
		HeroNode temp = head.next;
		while (true) {
//            判断是否到链表最后
			if (temp == null) {
				break;
			}
			System.out.println(temp);
//            将next后移
			temp = temp.next;
		}

	}


	public static SingleLinkedList reverse(SingleLinkedList list) {
		SingleLinkedList linkedList = new SingleLinkedList();
		HeroNode temp = list.head.next;

		while (true) {
			linkedList.headAdd(temp);
//            判断是否到链表最后
			if (temp.next == null) {
				break;
			}
//            System.out.println(temp);
//            将next后移
			temp = temp.next;
		}
		return linkedList;
	}

	//    链表头插法
	public void headAdd(HeroNode heroNode) {
//        判断头结点后面是否存在节点，不存在将添加的加在头结点后面
		HeroNode temp = heroNode;
		heroNode = new HeroNode(temp.getNo(), temp.getName(), temp.getNickname());
		heroNode.next = null;
		if (head.next == null) {
			head.next = heroNode;
			return;
		}
//        如果存在用传入的节点的next记录之后的节点
		heroNode.next = head.next;
//        将head的next指向传入的节点
		head.next = heroNode;
	}

	/**
	 * 单链表反转
	 *
	 * @param head
	 */
	public void reverseList(HeroNode head) {
//	    如果传入的对象或者链表为空，或者只有一个节点直接返回即可
		if (head == null || head.next == null || head.next.next == null) {
			return;
		}
		HeroNode temp = head.next;
		HeroNode next = null;
		HeroNode reverseHead = new HeroNode(0, "", "");
//        遍历原来的链表，每取出一个节点将其插入新的链表的头部
		while (temp != null) {
//            暂时保存当前节点的下一个节点
			next = temp.next;
			temp.next = reverseHead.next;
			reverseHead.next = temp;
			temp = next;
		}
		head.next = reverseHead.next;

	}
}

//定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
	public int no;
	private String name;
	private String nickname;
	public HeroNode next;//指向下一个节点

	public HeroNode() {
	}

	public HeroNode(int no, String name, String nickname) {
		this.no = no;
		this.name = name;
		this.nickname = nickname;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "HeroNode{" +
				"no=" + no +
				", name='" + name + '\'' +
				", nickname='" + nickname +
				'}';
	}
}