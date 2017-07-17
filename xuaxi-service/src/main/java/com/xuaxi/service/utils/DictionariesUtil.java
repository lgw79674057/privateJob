package com.xuaxi.service.utils;

import java.util.HashMap;
import java.util.Map;

public class DictionariesUtil {

	private static Map<String,String> nationality=new HashMap<String,String>();
	
	private static Map<String,String> cardType=new HashMap<String,String>();
	
	private static Map<String,String> certType=new HashMap<String,String>();
	
	static{
		nationality.put("AFG","阿富汗");
		nationality.put("AHO","荷属安的列斯");
		nationality.put("ALB","阿尔巴尼亚");
		nationality.put("ALG","阿尔及利亚");
		nationality.put("AND","安道尔");
		nationality.put("ANG","安哥拉");
		nationality.put("ANT","安提瓜和巴布达");
		nationality.put("ARG","阿根廷");
		nationality.put("ARM","亚美尼亚");
		nationality.put("ARU","阿鲁巴");
		nationality.put("ASA","美属萨摩亚");
		nationality.put("AUS","澳大利亚");
		nationality.put("AUT","奥地利");
		nationality.put("AZE","阿塞拜疆");
		nationality.put("BAH","巴哈马");
		nationality.put("BAN","孟加拉国");
		nationality.put("BAR","巴巴多斯");
		nationality.put("BDI","布隆迪");
		nationality.put("BEL","比利时");
		nationality.put("BEN","贝宁");
		nationality.put("BER","百慕大");
		nationality.put("BHU","不丹");
		nationality.put("BIH","波黑");
		nationality.put("BIZ","伯利兹");
		nationality.put("BLR","白俄罗斯");
		nationality.put("BOL","玻利维亚");
		nationality.put("BOT","博茨瓦纳");
		nationality.put("BRA","巴西");
		nationality.put("BRN","巴林");
		nationality.put("BRU","文莱");
		nationality.put("BUL","保加利亚");
		nationality.put("BUR","布基纳法索");
		nationality.put("CAF","中非");
		nationality.put("CAM","柬埔寨");
		nationality.put("CAN","加拿大");
		nationality.put("CAY","开曼群岛");
		nationality.put("CGO","刚果(布)");
		nationality.put("CHA","乍得");
		nationality.put("CHI","智利");
		nationality.put("CHN","中国");
		nationality.put("CIV","科特迪瓦");
		nationality.put("CMR","喀麦隆");
		nationality.put("COD","刚果（金）");
		nationality.put("COK","库克群岛");
		nationality.put("COL","哥伦比亚");
		nationality.put("COM","科摩罗");
		nationality.put("CPV","佛得角");
		nationality.put("CRC","哥斯达黎加");
		nationality.put("CRO","克罗地亚");
		nationality.put("CUB","古巴");
		nationality.put("CYP","塞浦路斯");
		nationality.put("CZE","捷克");
		nationality.put("DEN","丹麦");
		nationality.put("DJI","吉布提");
		nationality.put("DMA","多米尼加");
		nationality.put("DOM","多米尼加共和国");
		nationality.put("ECU","厄瓜多尔");
		nationality.put("EGY","埃及");
		nationality.put("ERI","厄立特里亚");
		nationality.put("ESA","萨尔瓦多");
		nationality.put("ESP","西班牙");
		nationality.put("EST","爱沙尼亚");
		nationality.put("ETH","埃塞俄比亚");
		nationality.put("FIJ","斐济");
		nationality.put("FIN","芬兰");
		nationality.put("FRA","法国");
		nationality.put("FSM","密克罗尼西亚联邦");
		nationality.put("GAB","加蓬");
		nationality.put("GAM","冈比亚");
		nationality.put("GBR","英国");
		nationality.put("GBS","几内亚比绍");
		nationality.put("GEO","格鲁吉亚");
		nationality.put("GEQ","赤道几内亚");
		nationality.put("GER","德国");
		nationality.put("GHA","加纳");
		nationality.put("GRE","希腊");
		nationality.put("GRN","格拉纳达");
		nationality.put("GUA","危地马拉");
		nationality.put("GUI","几内亚");
		nationality.put("GUM","关岛");
		nationality.put("GUY","圭亚那");
		nationality.put("HAI","海地");
		nationality.put("HKG","中国香港");
		nationality.put("HON","洪都拉斯");
		nationality.put("HUN","匈牙利");
		nationality.put("INA","印度尼西亚");
		nationality.put("IND","印度");
		nationality.put("IRI","伊朗");
		nationality.put("IRL","爱尔兰");
		nationality.put("IRQ","伊拉克");
		nationality.put("ISL","冰岛");
		nationality.put("ISR","以色列");
		nationality.put("ISV","美属维尔京群岛");
		nationality.put("ITA","意大利");
		nationality.put("IVB","英属维尔京群岛");
		nationality.put("JAM","牙买加");
		nationality.put("JOR","约旦");
		nationality.put("JPN","日本");
		nationality.put("KAZ","哈萨克斯坦");
		nationality.put("KEN","肯尼亚");
		nationality.put("KGZ","吉尔吉斯斯坦");
		nationality.put("KIR","基里巴斯");
		nationality.put("KOR","韩国");
		nationality.put("KSA","沙特");
		nationality.put("KUW","科威特");
		nationality.put("LAO","老挝");
		nationality.put("LAT","拉脱维亚");
		nationality.put("LBA","利比亚");
		nationality.put("LBR","利比里亚");
		nationality.put("LCA","圣卢西亚");
		nationality.put("LES","莱索托");
		nationality.put("LIB","黎巴嫩");
		nationality.put("LIE","列支敦士登");
		nationality.put("LTU","立陶宛");
		nationality.put("LUX","卢森堡");
		nationality.put("MAD","马达加斯加");
		nationality.put("MAR","摩洛哥");
		nationality.put("MAS","马来西亚");
		nationality.put("MAW","马拉维");
		nationality.put("MDA","摩尔多瓦");
		nationality.put("MDV","马尔代夫");
		nationality.put("MEX","墨西哥");
		nationality.put("MGL","蒙古");
		nationality.put("MHL","马绍尔群岛");
		nationality.put("MKD","马其顿");
		nationality.put("MLI","马里");
		nationality.put("MLT","马耳他");
		nationality.put("MNE","黑山");
		nationality.put("MON","摩纳哥");
		nationality.put("MOZ","莫桑比克");
		nationality.put("MRI","毛里求斯");
		nationality.put("MTN","毛里塔尼亚");
		nationality.put("MYA","缅甸");
		nationality.put("NAM","纳米比亚");
		nationality.put("NCA","尼加拉瓜");
		nationality.put("NED","荷兰");
		nationality.put("NEP","尼泊尔");
		nationality.put("NGR","尼日利亚");
		nationality.put("NIG","尼日尔");
		nationality.put("NOR","挪威");
		nationality.put("NRU","瑙鲁");
		nationality.put("NZL","新西兰");
		nationality.put("OMA","阿曼");
		nationality.put("PAK","巴基斯坦");
		nationality.put("PAN","巴拿马");
		nationality.put("PAR","巴拉圭");
		nationality.put("PER","秘鲁");
		nationality.put("PHI","菲律宾");
		nationality.put("PLE","巴勒斯坦");
		nationality.put("PLW","帕劳");
		nationality.put("PNG","巴布亚新几内亚");
		nationality.put("POL","波兰");
		nationality.put("POR","葡萄牙");
		nationality.put("PRK","朝鲜");
		nationality.put("PUR","波多黎各");
		nationality.put("QAT","卡塔尔");
		nationality.put("ROU","罗马尼亚");
		nationality.put("RSA","南非");
		nationality.put("RUS","俄罗斯");
		nationality.put("RWA","卢旺达");
		nationality.put("SAM","萨摩亚");
		nationality.put("SEN","塞内加尔");
		nationality.put("SEY","塞舌尔");
		nationality.put("SIN","新加坡");
		nationality.put("SKN","圣基茨和尼维斯");
		nationality.put("SLE","塞拉利昂");
		nationality.put("SLO","斯洛文尼亚");
		nationality.put("SMR","圣马力诺");
		nationality.put("SOL","所罗门群岛");
		nationality.put("SOM","索马里");
		nationality.put("SRB","塞尔维亚");
		nationality.put("SRI","斯里兰卡");
		nationality.put("STP","圣多美和普林西比");
		nationality.put("SUD","苏丹");
		nationality.put("SUI","瑞士");
		nationality.put("SUR","苏里南");
		nationality.put("SVK","斯洛伐克");
		nationality.put("SWE","瑞典");
		nationality.put("SWZ","斯威士兰");
		nationality.put("SYR","叙利亚");
		nationality.put("TAN","坦桑尼亚");
		nationality.put("TGA","汤加");
		nationality.put("THA","泰国");
		nationality.put("TJK","塔吉克斯坦");
		nationality.put("TKM","土库曼斯坦");
		nationality.put("TLS","东帝汶");
		nationality.put("TOG","多哥");
		nationality.put("TPE","中华台北");
		nationality.put("TRI","特立尼达和多巴哥");
		nationality.put("TUN","突尼斯");
		nationality.put("TUR","土耳其");
		nationality.put("TUV","图瓦卢");
		nationality.put("UAE","阿联酋");
		nationality.put("UGA","乌干达");
		nationality.put("UKR","乌克兰");
		nationality.put("URU","乌拉圭");
		nationality.put("USA","美国");
		nationality.put("UZB","乌兹别克斯坦");
		nationality.put("VAN","瓦努阿图");
		nationality.put("VEN","委内瑞拉");
		nationality.put("VIE","越南");
		nationality.put("VIN","圣文森特和格林纳丁斯");
		nationality.put("YEM","也门");
		nationality.put("ZAM","赞比亚");
		nationality.put("ZIM","津巴布韦");
		
		cardType.put("1", "身份证");
		cardType.put("2", "护照");
		cardType.put("3", "军官证");
		cardType.put("4", "其他");
		
		certType.put("1", "统一社会信用代码证");
		certType.put("2", "营业执照");
		certType.put("3", "税务登记证");
		certType.put("4", "组织机构代码证");
		certType.put("5", "其他");

	}
	public static Map<String,String> getNationality() {
		return nationality;
	}
	
	public static String getNationalityByName(String key) {
		return nationality.get(key);
	}
	
	public static Map<String,String> getCardType() {
		return cardType;
	}
	
	public static String getCardTypeByName(String key) {
		return cardType.get(key);
	}
	
	public static Map<String,String> getCertType() {
		return certType;
	}
	
	public static String getCertTypeByName(String key) {
		return certType.get(key);
	}
}
