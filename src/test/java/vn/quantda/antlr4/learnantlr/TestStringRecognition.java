package vn.quantda.antlr4.learnantlr;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.quantda.antlr4.learnantlr.ShapePlacerParser.ProgramContext;
import vn.quantda.antlr4.learnantlr.visitor.BasicDumpVisitor;

public class TestStringRecognition {
	private static final Logger logger = LoggerFactory.getLogger(TestStringRecognition.class);

	@Test
	public void testExploratoryString() throws IOException {

		String simplestProgram = "sphere 12 12 12 cube 2 3 4 cube 4 4 4 sphere 3 3 3";

		CharStream inputCharStream = new ANTLRInputStream(new StringReader(simplestProgram));
		TokenSource tokenSource = new ShapePlacerLexer(inputCharStream);
		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		ShapePlacerParser parser = new ShapePlacerParser(inputTokenStream);

		parser.addErrorListener(new TestErrorListener());

		ProgramContext context = parser.program();

		logger.info(context.toString());
	}

	@Test
	public void testJsonVisitor() throws IOException {
		//String program = "sphere 0 0 0 cube 5 5 5 sphere 10 1 3";
		String program = "sphere 1 1 1 cube 2 3 4 cube 4 4 4 sphere 3 3 3";
		TestErrorListener errorListener = new TestErrorListener();
		ProgramContext context = parseProgram(program, errorListener);

		assertFalse(errorListener.isFail());

		BasicDumpVisitor visitor = new BasicDumpVisitor();

		String jsonRepresentation = context.accept(visitor);
		logger.info("String return by the visitor = " + jsonRepresentation);
		// assertTrue(isValidString(jsonRepresentation));
	}

	private ProgramContext parseProgram(String program, TestErrorListener errorListener) throws IOException {
		CharStream inputCharStream = new ANTLRInputStream(new StringReader(program));
		TokenSource tokenSource = new ShapePlacerLexer(inputCharStream);
		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		ShapePlacerParser parser = new ShapePlacerParser(inputTokenStream);
		parser.addErrorListener(errorListener);
		
		ProgramContext context = parser.program();
		return context;
	}

	class TestErrorListener implements ANTLRErrorListener {
		private boolean fail = false;

		public boolean isFail() {
			return fail;
		}

		public void setFail(boolean fail) {
			this.fail = fail;
		}

		@Override
		public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
				RecognitionException arg5) {
			setFail(true);
		}

		@Override
		public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4, ATNConfigSet arg5) {
			setFail(true);
		}

		@Override
		public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4,
				ATNConfigSet arg5) {
			setFail(true);
		}

		@Override
		public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, BitSet arg5,
				ATNConfigSet arg6) {
			setFail(true);
		}
	}
}
