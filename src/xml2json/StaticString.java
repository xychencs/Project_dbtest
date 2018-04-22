package xml2json;

public interface StaticString {

	public final static String Visable ="\"width\": \"60\",\r\n" + 
			"				\"height\": \"60\"";
	
	public final static String unVisable ="\"width\": \"0\",\r\n" + 
			"				\"height\": \"0\"";
	
	public final static String sLink = "{\"type\": \"link\",\r\n" + 
			"		\"source\": {\r\n" + 
			"			\"id\": \"source_id\",\r\n" + 
			"			\"selector\": \"g:nth-child(1) > g:nth-child(1) > image:nth-child(1)\",\r\n" + 
			"			\"port\": null\r\n" + 
			"		},\r\n" + 
			"		\"target\": {\r\n" + 
			"			\"id\": \"target_id\"\r\n" + 
			"		},\r\n" + 
			"		\"id\": \"owner_id\",\r\n" + 
			"		\"z\": 4,\r\n" + 
			"		\"parent\": \"\",\r\n" + 
			"		\"closed\": 1,\r\n" + 
			"		\"attrs\": {\r\n" + 
			"			\".marker-target\": {\r\n" + 
			"				\"fill\": \"black\",\r\n" + 
			"				\"d\": \"M 10 0 L 0 5 L 10 10 z\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}";
	public final static String sLink_2 ="{\"type\": \"link\",\r\n" + 
			"		\"source\": {\r\n" + 
			"			\"id\": \"source_id\",\r\n" + 
			"			\"selector\": \"g:nth-child(1) > g:nth-child(1) > image:nth-child(1)\",\r\n" + 
			"			\"port\": null\r\n" + 
			"		},\r\n" + 
			"		\"target\": {\r\n" + 
			"			\"id\": \"target_id\"\r\n" + 
			"		},\r\n" + 
			"		\"id\": \"owner_id\",\r\n" + 
			"		\"z\": 51,\r\n" + 
			"		\"parent\": \"parent_id\",\r\n" + 
			"		\"closed\": 0,\r\n" + 
			"		\"attrs\": {\r\n" + 
			"			\".marker-target\": {\r\n" + 
			"				\"fill\": \"black\",\r\n" + 
			"				\"d\": \"M 10 0 L 0 5 L 10 10 z\",\r\n" + 
			"				\"style\": {\r\n" + 
			"					\"display\": \"none\"\r\n" + 
			"				}\r\n" + 
			"			},\r\n" + 
			"			\".connection\": {\r\n" + 
			"				\"style\": {\r\n" + 
			"					\"display\": \"none\"\r\n" + 
			"				}\r\n" + 
			"			},\r\n" + 
			"			\".connection-wrap\": {\r\n" + 
			"				\"style\": {\r\n" + 
			"					\"display\": \"none\"\r\n" + 
			"				}\r\n" + 
			"			},\r\n" + 
			"			\".marker-arrowheads\": {\r\n" + 
			"				\"style\": {\r\n" + 
			"					\"display\": \"none\"\r\n" + 
			"				}\r\n" + 
			"			},\r\n" + 
			"			\".link-tools\": {\r\n" + 
			"				\"style\": {\r\n" + 
			"					\"display\": \"none\"\r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}";
	
	public final static String sCreateTable = "{\r\n" + 
			"	\"type\": \"basic.CreateTable\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 0,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"TABLENAME\": {\r\n" + 
			"			\"text\": \"attrs_TABLENAME\"\r\n" + 
			"		},\r\n" + 
			"		\"COLUMNS\": {\r\n" + 
			"			\"text\": \"attrs_COLUMNS\"\r\n" + 
			"		},\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"attrs_SETCONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sUserLogin ="	{\r\n" + 
			"	\"type\": \"basic.UserLogin\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 4,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"image\": {\r\n" + 
			"			imageSize\r\n" + 
			"		},\r\n" + 
			"		\"UID\": {\r\n" + 
			"			\"text\": \"attrs_UID\"\r\n" + 
			"		},\r\n" + 
			"		\"PWD\": {\r\n" + 
			"			\"text\": \"attrs_PWD\"\r\n" + 
			"		},\r\n" + 
			"		\"CONNECT\": {\r\n" + 
			"			\"text\": \"attrs_CONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		},\r\n" + 
			"		\"rect\": {\r\n" + 
			"			\"magnet\": \"\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sCreateUser = "{\r\n" + 
			"	\"type\": \"basic.CreateUser\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 0,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"image\": {\r\n" + 
			"				imageSize\r\n" + 
			"		},\r\n" + 
			"		\"NEWUID\": {\r\n" + 
			"			\"text\": \"attrs_NEWUID\"\r\n" + 
			"		},\r\n" + 
			"		\"NEWPWD\": {\r\n" + 
			"			\"text\": \"attrs_NEWPWD\"\r\n" + 
			"		},\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"attrs_SETCONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		},\r\n" + 
			"		\"rect\": {\r\n" + 
			"			\"magnet\": \"\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";

	public final static String sCreateView ="{\r\n" + 
			"	\"type\": \"basic.CreateView\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 0,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"VIEWNAME\": {\r\n" + 
			"			\"text\": \"attrs_VIEWNAME\"\r\n" + 
			"		},\r\n" + 
			"		\"CONTENTS\": {\r\n" + 
			"			\"text\": \"attrs_CONTENTS\"\r\n" + 
			"		},\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"attrs_SETCONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sInsert ="{\r\n" + 
			"	\"type\": \"basic.Insert\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 1,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"INSERTTARGET\": {\r\n" + 
			"			\"text\": \"attrs_INSERTTARGET\"\r\n" + 
			"		},\r\n" + 
			"		\"VALUES\": {\r\n" + 
			"			\"text\": \"attrs_VALUES\"\r\n" + 
			"		},\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sDelete ="{\r\n" + 
			"	\"type\": \"basic.Delete\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 1,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"attrs_SETCONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"DELETETABLE\": {\r\n" + 
			"			\"text\": \"attrs_DELETETABLE\"\r\n" + 
			"		},\r\n" + 
			"		\"DELETEVIEW\": {\r\n" + 
			"			\"text\": \"attrs_DELETEVIEW\"\r\n" + 
			"		},\r\n" + 
			"		\"WHERE\": {\r\n" + 
			"			\"text\": \"attrs_WHERE\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sUpdate ="{\r\n" + 
			"	\"type\": \"basic.Update\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 2,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"SETCONNECT\": {\r\n" + 
			"			\"text\": \"attrs_SETCONNECT\"\r\n" + 
			"		},\r\n" + 
			"		\"UPDATETARGET\": {\r\n" + 
			"			\"text\": \"attrs_UPDATETARGET\"\r\n" + 
			"		},\r\n" + 
			"		\"SET\": {\r\n" + 
			"			\"text\": \"attrs_SET_\"\r\n" + 
			"		},\r\n" + 
			"		\"WHERE\": {\r\n" + 
			"			\"text\": \"attrs_WHERE\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sSelect ="{\r\n" + 
			"	\"type\": \"basic.Select\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\":\"parent_id\",\r\n" + 
			"	\"z\": 1,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"SQL\": {\r\n" + 
			"			\"text\": \"attrs_SQL\"\r\n" + 
			"		},\r\n" + 
			"		\"TYPE\": {\r\n" + 
			"			\"text\": \"attrs_TYPE\"\r\n" + 
			"		},\r\n" + 
			"		\"COLUMN\": {\r\n" + 
			"			\"text\": \"attrs_COLUMN\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	
	public final static String sTrans ="";
	public final static String sSetTrans ="";
	public final static String sGrant ="";
	
	
	
	public final static String sAlterPwd ="{\r\n" + 
			"		\"type\": \"basic.AlterPwd\",\r\n" + 
			"		\"position\": {\r\n" + 
			"			\"x\": position_x,\r\n" + 
			"			\"y\": position_y\r\n" + 
			"		},\r\n" + 
			"		\"size\": {\r\n" + 
			"			\"width\": 60,\r\n" + 
			"			\"height\": 60\r\n" + 
			"		},\r\n" + 
			"		\"angle\": 0,\r\n" + 
			"		\"id\": \"owner_id\",\r\n" + 
			"		\"closed\": closed_id,\r\n" + 
			"		\"z\": 1,\r\n" + 
			"		\"attrs\": {\r\n" + 
			"			\"USER\": {\r\n" + 
			"				\"text\": \"attrs_USER\"\r\n" + 
			"			},\r\n" + 
			"			\"PWD\": {\r\n" + 
			"				\"text\": \"attrs_PWD\"\r\n" + 
			"			},\r\n" + 
			"			\"TYPE\": {\r\n" + 
			"				\"text\": \"attrs_TYPE\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}";
	public final static String sSetVal ="{\r\n" + 
			"		\"type\": \"basic.SetVal\",\r\n" + 
			"		\"position\": {\r\n" + 
			"			\"x\": position_x,\r\n" + 
			"			\"y\": position_y\r\n" + 
			"		},\r\n" + 
			"		\"size\": {\r\n" + 
			"			\"width\": 60,\r\n" + 
			"			\"height\": 60\r\n" + 
			"		},\r\n" + 
			"		\"angle\": 0,\r\n" + 
			"		\"id\": \"owner_id\",\r\n" + 
			"		\"closed\": closed_id,\r\n" + 
			"		\"z\": 1,\r\n" + 
			"		\"attrs\": {\r\n" + 
			"			\"VALNAME\": {\r\n" + 
			"				\"text\": \"attrs_VALNAME\"\r\n" + 
			"			},\r\n" + 
			"			\"VAL\": {\r\n" + 
			"				\"text\": \"attrs_VAL\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}";
	public final static String sGetVal ="{\r\n" + 
			"		\"type\": \"basic.GetVal\",\r\n" + 
			"		\"position\": {\r\n" + 
			"			\"x\": position_x,\r\n" + 
			"			\"y\": position_y\r\n" + 
			"		},\r\n" + 
			"		\"size\": {\r\n" + 
			"			\"width\": 60,\r\n" + 
			"			\"height\": 60\r\n" + 
			"		},\r\n" + 
			"		\"angle\": 0,\r\n" + 
			"		\"id\": \"owner_id\",\r\n" + 
			"		\"closed\": closed_id,\r\n" + 
			"		\"z\": 2,\r\n" + 
			"		\"attrs\": {\r\n" + 
			"			\"VALNAME\": {\r\n" + 
			"				\"text\": \"attrs_VALNAME\"\r\n" + 
			"			},\r\n" + 
			"			\"VAL\": {\r\n" + 
			"				\"text\": \"attrs_VAL\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}";
	public final static String sPrint ="";
	public final static String sFor ="{\r\n" + 
			"	\"type\": \"basic.For\",\r\n" + 
			"	\"position\": {\r\n" + 
			"		\"x\": position_x,\r\n" + 
			"		\"y\": position_y\r\n" + 
			"	},\r\n" + 
			"	\"size\": {\r\n" + 
			"		\"width\": 60,\r\n" + 
			"		\"height\": 60\r\n" + 
			"	},\r\n" + 
			"	\"angle\": 0,\r\n" + 
			"	\"id\": \"owner_id\",\r\n" + 
			"	\"closed\": closed_id,\r\n" + 
			"	\"parent\": \"parent_id\",\r\n" + 
			"	\"embeds\": [embeds_id],\r\n" + 
			"	\"z\": 1,\r\n" + 
			"	\"attrs\": {\r\n" + 
			"		\"image\": {\r\n" + 
			"				imageSize\r\n" + 
			"			},\r\n" + 
			"		\"SQLVAL1\": {\r\n" + 
			"			\"text\": \"attrs_SQLVAL1\"\r\n" + 
			"		},\r\n" + 
			"		\"FORTIMES\": {\r\n" + 
			"			\"text\": \"attrs_FORTIMES\"\r\n" + 
			"		},\r\n" + 
			"		\"EXP\": {\r\n" + 
			"			\"text\": \"attrs_EXP\"\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}";
	public final static String sConditionalBranch ="";
	public final static String sLock ="";
	public final static String sMultipleThread ="";
	public final static String sSleep ="";
	public final static String sRemoteCMD ="";
	public final static String sRunRemote ="";
	public final static String sInteract ="";
	public final static String sRunImport ="";
	public final static String sGeneral ="";
	
}

