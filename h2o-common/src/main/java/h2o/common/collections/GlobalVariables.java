package h2o.common.collections;


@SuppressWarnings("unchecked")
public class GlobalVariables {
	
	public static volatile Object v0;
	
	public static volatile Object v1;
	
	public static volatile Object v2;
	
	public static volatile Object v3;	
	
	public static volatile Object v4;	
	
	public static volatile Object v5;	
	
	public static volatile Object v6;
	
	public static volatile Object v7;
	
	public static volatile Object v8;
	
	public static volatile Object v9;
	
	public static volatile Object v10;

	
	public <T> T getV0() {
		return (T)v0;
	}

	public void setV0(Object v0) {
		GlobalVariables.v0 = v0;
	}

	public <T> T  getV1() {
		return (T)v1;
	}

	public void setV1(Object v1) {
		GlobalVariables.v1 = v1;
	}

	public <T> T  getV2() {
		return (T)v2;
	}

	public void setV2(Object v2) {
		GlobalVariables.v2 = v2;
	}

	public <T> T  getV3() {
		return (T)v3;
	}

	public void setV3(Object v3) {
		GlobalVariables.v3 = v3;
	}

	public <T> T  getV4() {
		return (T)v4;
	}

	public void setV4(Object v4) {
		GlobalVariables.v4 = v4;
	}

	public <T> T  getV5() {
		return (T)v5;
	}

	public void setV5(Object v5) {
		GlobalVariables.v5 = v5;
	}

	public <T> T  getV6() {
		return (T)v6;
	}

	public void setV6(Object v6) {
		GlobalVariables.v6 = v6;
	}

	public <T> T  getV7() {
		return (T)v7;
	}

	public void setV7(Object v7) {
		GlobalVariables.v7 = v7;
	}

	public <T> T  getV8() {
		return (T)v8;
	}

	public void setV8(Object v8) {
		GlobalVariables.v8 = v8;
	}

	public <T> T  getV9() {
		return (T)v9;
	}

	public void setV9(Object v9) {
		GlobalVariables.v9 = v9;
	}

	public <T> T  getV10() {
		return (T)v10;
	}

	public void setV10(Object v10) {
		GlobalVariables.v10 = v10;
	}

	public Object[] getVs() {
		return new Object[] { v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10 };
	}
	
	@Override
	public String toString() {
		return "GlobalVariables [v0=" + v0 + ", v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + ", v4=" + v4 + ", v5=" + v5 + ", v6=" + v6 + ", v7=" + v7 + ", v8=" + v8 + ", v9=" + v9 + ", v10=" + v10 + "]";
	}	

}
