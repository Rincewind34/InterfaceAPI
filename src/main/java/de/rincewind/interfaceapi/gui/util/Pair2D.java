package de.rincewind.interfaceapi.gui.util;

class Pair2D {
	
	protected final int first;
	protected final int second;
	
	protected Pair2D(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		Pair2D other = (Pair2D) obj;

		if (this.first != other.first || this.second != other.second) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.first;
		result = prime * result + this.second;
		return result;
	}
	
}
