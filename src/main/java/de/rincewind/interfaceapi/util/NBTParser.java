package de.rincewind.interfaceapi.util;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.server.v1_13_R1.NBTBase;
import net.minecraft.server.v1_13_R1.NBTTagByte;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.NBTTagDouble;
import net.minecraft.server.v1_13_R1.NBTTagFloat;
import net.minecraft.server.v1_13_R1.NBTTagInt;
import net.minecraft.server.v1_13_R1.NBTTagIntArray;
import net.minecraft.server.v1_13_R1.NBTTagList;
import net.minecraft.server.v1_13_R1.NBTTagLong;
import net.minecraft.server.v1_13_R1.NBTTagShort;
import net.minecraft.server.v1_13_R1.NBTTagString;

public class NBTParser {

	private static final Pattern b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");

	public static NBTTagCompound parse(String s) throws Exception {
		s = s.trim();
		if (!s.startsWith("{")) {
			throw new Exception("Invalid tag encountered, expected \'{\' as first char.");
		} else if (b(s) != 1) {
			throw new Exception("Encountered multiple top tags, only one expected");
		} else {
			return (NBTTagCompound) a("tag", s).a();
		}
	}

	static int b(String s) throws Exception {
		int i = 0;
		boolean flag = false;
		Stack<Character> stack = new Stack<>();

		for (int j = 0; j < s.length(); ++j) {
			char c0 = s.charAt(j);

			if (c0 == '"') {
				if (NBTParser.isCharExcaped(s, j)) {
					if (!flag) {
						throw new Exception("Illegal use of \\\": " + s);
					}
				} else {
					flag = !flag;
				}
			} else if (!flag) {
				if (c0 != '{' && c0 != '[') {
					if (c0 == 125 && (stack.isEmpty() || ((Character) stack.pop()).charValue() != '{')) {
						throw new Exception("Unbalanced curly brackets {}: " + s);
					}

					if (c0 == 93 && (stack.isEmpty() || ((Character) stack.pop()).charValue() != '[')) {
						throw new Exception("Unbalanced square brackets []: " + s);
					}
				} else {
					if (stack.isEmpty()) {
						++i;
					}

					stack.push(Character.valueOf(c0));
				}
			}
		}

		if (flag) {
			throw new Exception("Unbalanced quotation: " + s);
		} else if (!stack.isEmpty()) {
			throw new Exception("Unbalanced brackets: " + s);
		} else {
			if (i == 0 && !s.isEmpty()) {
				i = 1;
			}

			return i;
		}
	}

	static NBTParser.NBTTypeParser a(String... astring) throws Exception {
		return a(astring[0], astring[1]);
	}

	static NBTParser.NBTTypeParser a(String s, String input) throws Exception {
		input = input.trim();

		String s2;
		boolean flag;
		char c0;

		if (input.startsWith("{")) {
			input = input.substring(1, input.length() - 1);
			
			NBTCompoundParser parser = new NBTCompoundParser(s);

			for (; input.length() > 0; input = input.substring(s2.length() + 1)) {
				s2 = b(input, true);
				
				if (s2.length() > 0) {
					flag = false;
					parser.b.add(a(s2, flag));
				}

				if (input.length() < s2.length() + 1) {
					break;
				}

				c0 = input.charAt(s2.length());
				
				if (c0 != 44 && c0 != '{' && c0 != 125 && c0 != '[' && c0 != 93) {
					throw new Exception("Unexpected token \'" + c0 + "\' at: " + input.substring(s2.length()));
				}
			}

			return parser;
		} else if (input.startsWith("[") && !NBTParser.b.matcher(input).matches()) {
			input = input.substring(1, input.length() - 1);

			NBTParser.NBTListParser parser;

			for (parser = new NBTParser.NBTListParser(s); input.length() > 0; input = input.substring(s2.length() + 1)) {
				s2 = b(input, false);
				
				if (s2.length() > 0) {
					flag = true;
					parser.b.add(a(s2, flag));
				}

				if (input.length() < s2.length() + 1) {
					break;
				}

				c0 = input.charAt(s2.length());
				
				if (c0 != 44 && c0 != '{' && c0 != 125 && c0 != '[' && c0 != 93) {
					throw new Exception("Unexpected token \'" + c0 + "\' at: " + input.substring(s2.length()));
				}
			}

			return parser;
		} else {
			return new NBTParser.NBTPrimitiveParser(s, input);
		}
	}

	private static NBTParser.NBTTypeParser a(String s, boolean flag) throws Exception {
		String s1 = c(s, flag);
		String s2 = d(s, flag);

		return a(new String[] { s1, s2 });
	}

	private static String b(String input, boolean flag) throws Exception {
		int i = NBTParser.indexOf(input, ':');
		int j = NBTParser.indexOf(input, ',');

		if (flag) {
			if (i == -1) {
				throw new Exception("Unable to locate name/value separator for string: " + input);
			}

			if (j != -1 && j < i) {
				throw new Exception("Name error at: " + input);
			}
		} else if (i == -1 || i > j) {
			i = -1;
		}

		return a(input, i);
	}

	private static String a(String s, int i) throws Exception {
		Stack<Character> stack = new Stack<>();
		int j = i + 1;
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;

		for (int k = 0; j < s.length(); ++j) {
			char c0 = s.charAt(j);

			if (c0 == '"') {
				if (NBTParser.isCharExcaped(s, j)) {
					if (!flag) {
						throw new Exception("Illegal use of \\\": " + s);
					}
				} else {
					flag = !flag;
					if (flag && !flag2) {
						flag1 = true;
					}

					if (!flag) {
						k = j;
					}
				}
			} else if (!flag) {
				if (c0 != '{' && c0 != '[') {
					if (c0 == 125 && (stack.isEmpty() || ((Character) stack.pop()).charValue() != '{')) {
						throw new Exception("Unbalanced curly brackets {}: " + s);
					}

					if (c0 == 93 && (stack.isEmpty() || ((Character) stack.pop()).charValue() != '[')) {
						throw new Exception("Unbalanced square brackets []: " + s);
					}

					if (c0 == 44 && stack.isEmpty()) {
						return s.substring(0, j);
					}
				} else {
					stack.push(Character.valueOf(c0));
				}
			}

			if (!Character.isWhitespace(c0)) {
				if (!flag && flag1 && k != j) {
					return s.substring(0, k + 1);
				}

				flag2 = true;
			}
		}

		return s.substring(0, j);
	}

	private static String c(String s, boolean flag) throws Exception {
		if (flag) {
			s = s.trim();
			if (s.startsWith("{") || s.startsWith("[")) {
				return "";
			}
		}

		int i = NBTParser.indexOf(s, ':');

		if (i == -1) {
			if (flag) {
				return "";
			} else {
				throw new Exception("Unable to locate name/value separator for string: " + s);
			}
		} else {
			return s.substring(0, i).trim();
		}
	}

	private static String d(String s, boolean flag) throws Exception {
		if (flag) {
			s = s.trim();
			if (s.startsWith("{") || s.startsWith("[")) {
				return s;
			}
		}

		int i = NBTParser.indexOf(s, ':');

		if (i == -1) {
			if (flag) {
				return s;
			} else {
				throw new Exception("Unable to locate name/value separator for string: " + s);
			}
		} else {
			return s.substring(i + 1).trim();
		}
	}

	private static int indexOf(String input, char search) {
		boolean inString = false;

		for (int i = 0; i < input.length(); ++i) {
			char charAt = input.charAt(i);

			if (charAt == '"') {
				if (!NBTParser.isCharExcaped(input, i)) {
					inString = !inString;
				}
			} else if (!inString) {
				if (charAt == search) {
					return i;
				}

				if (charAt == '{' || charAt == '[') {
					return -1;
				}
			}
		}

		return -1;
	}

	private static boolean isCharExcaped(String s, int i) {
		return i > 0 && s.charAt(i - 1) == '\\' && !NBTParser.isCharExcaped(s, i - 1);
	}

	static class NBTPrimitiveParser extends NBTParser.NBTTypeParser {

		private static final Pattern c = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
		private static final Pattern d = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
		private static final Pattern e = Pattern.compile("[-+]?[0-9]+[b|B]");
		private static final Pattern f = Pattern.compile("[-+]?[0-9]+[l|L]");
		private static final Pattern g = Pattern.compile("[-+]?[0-9]+[s|S]");
		private static final Pattern h = Pattern.compile("[-+]?[0-9]+");
		private static final Pattern i = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
		private static final Splitter j = Splitter.on(',').omitEmptyStrings();
		protected String b;

		public NBTPrimitiveParser(String s, String s1) {
			this.a = s;
			this.b = s1;
		}

		@Override
		public NBTBase a() throws Exception {
			try {
				if (NBTParser.NBTPrimitiveParser.c.matcher(this.b).matches()) {
					return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length() - 1)));
				}

				if (NBTParser.NBTPrimitiveParser.d.matcher(this.b).matches()) {
					return new NBTTagFloat(Float.parseFloat(this.b.substring(0, this.b.length() - 1)));
				}

				if (NBTParser.NBTPrimitiveParser.e.matcher(this.b).matches()) {
					return new NBTTagByte(Byte.parseByte(this.b.substring(0, this.b.length() - 1)));
				}

				if (NBTParser.NBTPrimitiveParser.f.matcher(this.b).matches()) {
					return new NBTTagLong(Long.parseLong(this.b.substring(0, this.b.length() - 1)));
				}

				if (NBTParser.NBTPrimitiveParser.g.matcher(this.b).matches()) {
					return new NBTTagShort(Short.parseShort(this.b.substring(0, this.b.length() - 1)));
				}

				if (NBTParser.NBTPrimitiveParser.h.matcher(this.b).matches()) {
					return new NBTTagInt(Integer.parseInt(this.b));
				}

				if (NBTParser.NBTPrimitiveParser.i.matcher(this.b).matches()) {
					return new NBTTagDouble(Double.parseDouble(this.b));
				}

				if (this.b.equalsIgnoreCase("true") || this.b.equalsIgnoreCase("false")) {
					return new NBTTagByte((byte) (Boolean.parseBoolean(this.b) ? 1 : 0));
				}
			} catch (NumberFormatException numberformatexception) {
				this.b = this.b.replaceAll("\\\\\"", "\"");
				return new NBTTagString(this.b);
			}

			if (this.b.startsWith("[") && this.b.endsWith("]")) {
				String s = this.b.substring(1, this.b.length() - 1);
				String[] astring = (String[]) Iterables.toArray(NBTParser.NBTPrimitiveParser.j.split(s), String.class);

				try {
					int[] aint = new int[astring.length];

					for (int i = 0; i < astring.length; ++i) {
						aint[i] = Integer.parseInt(astring[i].trim());
					}

					return new NBTTagIntArray(aint);
				} catch (NumberFormatException numberformatexception1) {
					return new NBTTagString(this.b);
				}
			} else {
				if (this.b.startsWith("\"") && this.b.endsWith("\"")) {
					this.b = this.b.substring(1, this.b.length() - 1);
				}

				this.b = this.b.replaceAll("\\\\\"", "\"");
				StringBuilder stringbuilder = new StringBuilder();

				for (int j = 0; j < this.b.length(); ++j) {
					if (j < this.b.length() - 1 && this.b.charAt(j) == 92 && this.b.charAt(j + 1) == 92) {
						stringbuilder.append('\\');
						++j;
					} else {
						stringbuilder.append(this.b.charAt(j));
					}
				}

				return new NBTTagString(stringbuilder.toString());
			}
		}
	}

	static class NBTListParser extends NBTParser.NBTTypeParser {

		protected List<NBTParser.NBTTypeParser> b = Lists.newArrayList();

		public NBTListParser(String s) {
			this.a = s;
		}

		@Override
		public NBTBase a() throws Exception {
			NBTTagList nbttaglist = new NBTTagList();
			Iterator<NBTTypeParser> iterator = this.b.iterator();

			while (iterator.hasNext()) {
				NBTParser.NBTTypeParser NBTParser_mojangsontypeparser = (NBTParser.NBTTypeParser) iterator.next();

				nbttaglist.add(NBTParser_mojangsontypeparser.a());
			}

			return nbttaglist;
		}
	}

	static class NBTCompoundParser extends NBTParser.NBTTypeParser {

		protected List<NBTParser.NBTTypeParser> b = Lists.newArrayList();

		public NBTCompoundParser(String s) {
			this.a = s;
		}

		@Override
		public NBTBase a() throws Exception {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			Iterator<NBTParser.NBTTypeParser> iterator = this.b.iterator();

			while (iterator.hasNext()) {
				NBTTypeParser NBTParser_mojangsontypeparser = (NBTTypeParser) iterator.next();

				nbttagcompound.set(NBTParser_mojangsontypeparser.a, NBTParser_mojangsontypeparser.a());
			}

			return nbttagcompound;
		}
	}

	abstract static class NBTTypeParser {

		protected String a;

		NBTTypeParser() {
		}

		public abstract NBTBase a() throws Exception;
	}
}
