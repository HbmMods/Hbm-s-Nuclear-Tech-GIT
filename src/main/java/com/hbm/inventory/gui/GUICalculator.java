package com.hbm.inventory.gui;

import com.hbm.lib.RefStrings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;
import java.util.Stack;

public class GUICalculator extends GuiScreen {
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/calculator.png");
	private int xSize = 220;
	private int ySize = 50;
	private GuiTextField inputField;

	private String latestResult = "?";

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		inputField = new GuiTextField(fontRendererObj, x + 5, y + 8, 210, 13);
		inputField.setTextColor(-1);
		inputField.setCanLoseFocus(false);
		inputField.setFocused(true);
		inputField.setMaxStringLength(1000);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if (!inputField.textboxKeyTyped(p_73869_1_, p_73869_2_))
			super.keyTyped(p_73869_1_, p_73869_2_);

		String input = inputField.getText().replaceAll("[^\\d+\\-*/^!.()\\sA-Za-z]+", "");

		if (p_73869_1_ == 13 || p_73869_1_ == 10) { // when pressing enter (CR or LF)
			try {
				double result = evaluateExpression(input);
				String plainStringRepresentation = (new BigDecimal(result, MathContext.DECIMAL64)).toPlainString();
				GuiScreen.setClipboardString(plainStringRepresentation);
				inputField.setText(plainStringRepresentation);
				inputField.setCursorPositionEnd();
				inputField.setSelectionPos(0);
			} catch (Exception ignored) {}
			return;
		}

		if (input.isEmpty()) {
			latestResult = "?";
			return;
		}

		try {
			latestResult = Double.toString(evaluateExpression(input));
		} catch (Exception e) { latestResult = e.toString(); }
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		inputField.drawTextBox();
		fontRendererObj.drawString("=" + latestResult, x + 5, y + 30, -1);
	}

	/**
	 * Mathematically evaluates user-inputted strings<br>
	 * It is recommended to catch all exceptions when using this
	 */
	public static double evaluateExpression(String input) {
		if (input.contains("^")) input = preEvaluatePower(input);

		char[] tokens = input.toCharArray();
		Stack<Double> values = new Stack<>();
		Stack<String> operators = new Stack<>();

		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ') continue;

			if (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.' || (tokens[i] == '-' && (i == 0 || "+-*/^(".contains(String.valueOf(tokens[i - 1]))))) {
				StringBuilder buffer = new StringBuilder();
				if (tokens[i] == '-') {
					buffer.append('-'); // for negative numbers
					i++;
				}
				while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) buffer.append(tokens[i++]);
				values.push(Double.parseDouble(buffer.toString()));
				i--;
			} else if (tokens[i] == '(') operators.push(Character.toString(tokens[i]));
			else if (tokens[i] == ')') {
				while (!operators.isEmpty() && operators.peek().charAt(0) != '(')
					values.push(evaluateOperator(operators.pop().charAt(0), values.pop(), values.pop()));
				operators.pop();
				if (!operators.isEmpty() && operators.peek().length() > 1)
					values.push(evaluateFunction(operators.pop(), values.pop()));
			} else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
				while (!operators.isEmpty() && hasPrecedence(String.valueOf(tokens[i]), operators.peek()))
					values.push(evaluateOperator(operators.pop().charAt(0), values.pop(), values.pop()));
				operators.push(Character.toString(tokens[i]));
			} else if (tokens[i] == '!') {
				values.push((double) factorial((int) Math.round(values.pop())));
			}else if (tokens[i] >= 'A' && tokens[i] <= 'Z' || tokens[i] >= 'a' && tokens[i] <= 'z') {
				StringBuilder charBuffer = new StringBuilder();
				while (i < tokens.length && (tokens[i] >= 'A' && tokens[i] <= 'Z' || tokens[i] >= 'a' && tokens[i] <= 'z'))
					charBuffer.append(tokens[i++]);
				String string = charBuffer.toString();
				if (string.equalsIgnoreCase("pi")) values.push(Math.PI);
				else if (string.equalsIgnoreCase("e")) values.push(Math.E);
				else operators.push(string.toLowerCase(Locale.ROOT));
				i--;
			}
		}

		// if the expression is correctly formatted, no function is remaining
		while (!operators.empty()) values.push(evaluateOperator(operators.pop().charAt(0), values.pop(), values.pop()));

		return values.pop();
	}

	private static double evaluateOperator(char operator, double x, double y) {
		switch (operator) {
			case '+': return y + x;
			case '-': return y - x;
			case '*': return y * x;
			case '/': return y / x;

			case '^': return Math.pow(y, x); // should not happen here, but oh well
		}
		return 0;
	}

	private static double evaluateFunction(String function, double x) {
		switch (function) {
			case "sqrt": return Math.sqrt(x);

			case "sin": return Math.sin(x);
			case "cos": return Math.cos(x);
			case "tan": return Math.tan(x);
			case "asin": return Math.asin(x);
			case "acos": return Math.acos(x);
			case "atan": return Math.atan(x);

			case "log": return Math.log10(x);
			case "ln": return Math.log(x);

			case "ceil": return Math.ceil(x);
			case "floor": return Math.floor(x);
			case "round": return Math.round(x);
		}
		return 0;
	}

	/** Returns whether {@code second} has precedence over {@code first} */
	private static boolean hasPrecedence(String first, String second) {
		if (second.length() > 1) return false;

		char firstChar = first.charAt(0);
		char secondChar = second.charAt(0);

		if (secondChar == '(' || secondChar == ')') return false;
		else return (firstChar != '*' && firstChar != '/' && firstChar != '^') || (secondChar != '+' && secondChar != '-');
	}

	/** Returns the input with all powers evaluated */
	private static String preEvaluatePower(String input) {
		do {
			int powerOperatorIndex = input.lastIndexOf('^');

			// find base
			boolean previousTokenIsParentheses = input.charAt(powerOperatorIndex - 1) == ')';
			int parenthesesDepth = previousTokenIsParentheses ? 1 : 0;
			int baseExpressionStart = previousTokenIsParentheses ? powerOperatorIndex - 2 : powerOperatorIndex - 1;
			baseLoop:
			for (; baseExpressionStart >= 0; baseExpressionStart--) { // search backwards
				switch (input.charAt(baseExpressionStart)) {
					case ')':
						if (previousTokenIsParentheses) parenthesesDepth++;
						else break baseLoop;
						break;
					case '(':
						if (previousTokenIsParentheses && parenthesesDepth > 0) parenthesesDepth--;
						else break baseLoop;
						break;
					case '+': case '-': case '*': case '/': case '^':
						if (parenthesesDepth == 0) break baseLoop;
				}
			}
			baseExpressionStart++; // go one token forward again
			if (parenthesesDepth > 0) throw new IllegalArgumentException("Incomplete parentheses");

			// find exponent
			boolean nextTokenIsParentheses = input.charAt(powerOperatorIndex + 1) == '(';
			parenthesesDepth = nextTokenIsParentheses ? 1 : 0;
			int exponentExpressionEnd = nextTokenIsParentheses ? powerOperatorIndex + 2 : powerOperatorIndex + 1;
			exponentLoop:
			for (; exponentExpressionEnd < input.length(); exponentExpressionEnd++) {
				switch (input.charAt(exponentExpressionEnd)) {
					case '(':
						if (nextTokenIsParentheses) parenthesesDepth++;
						else break exponentLoop;
						break;
					case ')':
						if (nextTokenIsParentheses && parenthesesDepth > 0) parenthesesDepth--;
						else break exponentLoop;
						break;
					case '+': case '-': case '*': case '/': case '^':
						if (parenthesesDepth == 0) break exponentLoop;
				}
			}
			if (parenthesesDepth > 0) throw new IllegalArgumentException("Incomplete parentheses");

			double base = evaluateExpression(input.substring(baseExpressionStart, powerOperatorIndex));
			double exponent = evaluateExpression(input.substring(powerOperatorIndex + 1, exponentExpressionEnd));
			double result = Math.pow(base, exponent);
			// use big decimal to avoid scientific notation messing with the calculation
			input = input.substring(0, baseExpressionStart) + (new BigDecimal(result, MathContext.DECIMAL64)).toPlainString() + input.substring(exponentExpressionEnd);
		} while (input.contains("^"));

		return input;
	}

	// TODO Maybe switch the whole calculator to using BigInteger/BigDecimal?
	// SplitRecursive algorithm
	private static int factorial(int in) {
		if (in < 0) throw new IllegalArgumentException("Factorial needs n >= 0");
		if (in < 2) return 1;
		int p = 1, r = 1;
		factorialCurrentN = 1;
		int h = 0, shift = 0, high = 1;
		int log2n = log2(in);
		while (h != in) {
			shift += h;
			h = in >> log2n--;
			int len = high;
			high = (h - 1) | 1;
			len = (high - len) / 2;

			if (len > 0) {
				p *= factorialProduct(len);
				r *= p;
			}
		}

		return r << shift;
	}

	private static int factorialCurrentN;

	private static int factorialProduct(int in) {
		int m = in / 2;
		if (m == 0) return factorialCurrentN += 2;
		if (in == 2) return (factorialCurrentN += 2) * (factorialCurrentN += 2);
		return factorialProduct(in - m) * factorialProduct(m);
	}

	private static int log2(int in) {
		int log = 0;
		if((in & 0xffff0000) != 0) { in >>>= 16; log = 16; }
		if(in >= 256) { in >>>= 8; log += 8; }
		if(in >= 16) { in >>>= 4; log += 4; }
		if(in >= 4) { in >>>= 2; log += 2; }
		return log + (in >>> 1);
	}
}
