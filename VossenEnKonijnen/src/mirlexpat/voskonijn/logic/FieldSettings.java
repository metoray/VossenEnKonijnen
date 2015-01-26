package mirlexpat.voskonijn.logic;

import java.io.Serializable;

public class FieldSettings implements Serializable {
	
	private static final long serialVersionUID = -3696332681664249116L;
	private IntegerSetting width, depth;
	
	public FieldSettings(){
		width = new IntegerSetting(120);
		depth = new IntegerSetting(80);
	}
	
	public Field generateField(){
		return new Field(this);
	}
	
	public IntegerSetting getWidthSetting() {
		return width;
	}

	public IntegerSetting getDepthSetting() {
		return depth;
	}
	
	public boolean equals(Object o){
		if(o==this) return true;
		if(o instanceof FieldSettings){
			FieldSettings other = (FieldSettings) o;
			return other.depth.equals(depth)&&
				   other.width.equals(width);
		}
		return false;
	}

	public static class IntegerSetting implements Serializable {
		
		private static final long serialVersionUID = -5736047574958108476L;
		private int value;
		
		public IntegerSetting(int value){
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
		public boolean equals(Object o){
			if(o==this) return true;
			if(o instanceof IntegerSetting){
				return ((IntegerSetting) o).getValue() == value;
			}
			return false;
		}
		
	}
	

}
