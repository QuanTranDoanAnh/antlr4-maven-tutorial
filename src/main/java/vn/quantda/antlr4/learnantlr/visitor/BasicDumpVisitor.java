package vn.quantda.antlr4.learnantlr.visitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.quantda.antlr4.learnantlr.ShapePlacerParser.CoordinatesContext;
import vn.quantda.antlr4.learnantlr.ShapePlacerParser.CubeDefinitionContext;
import vn.quantda.antlr4.learnantlr.ShapePlacerParser.ProgramContext;
import vn.quantda.antlr4.learnantlr.ShapePlacerParser.ShapeDefinitionContext;
import vn.quantda.antlr4.learnantlr.ShapePlacerParser.SphereDefinitionContext;
import vn.quantda.antlr4.learnantlr.ShapePlacerVisitor;

public class BasicDumpVisitor implements ShapePlacerVisitor<String> {

	private static final Logger logger = LoggerFactory.getLogger(BasicDumpVisitor.class);
	
	@Override
	public String visit(ParseTree arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visitChildren(RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visitTerminal(TerminalNode arg0) {
		logger.info("visiting terminal node: " + arg0.toStringTree());
		return arg0.getText();
	}

	@Override
	public String visitProgram(ProgramContext ctx) {
		StringBuilder builder = new StringBuilder();
		for (ParseTree tree : ctx.children) {
			builder.append(tree.accept(this));
		}
		return builder.toString();
	}

	@Override
	public String visitShapeDefinition(ShapeDefinitionContext ctx) {
		StringBuilder builder = new StringBuilder();
		for (ParseTree tree : ctx.children) {
			builder.append(tree.accept(this) + " ");
		}
		builder.append("\n");
		return builder.toString();
	}

	@Override
	public String visitSphereDefinition(SphereDefinitionContext ctx) {
		return getShapeDefinitionString(ctx, "sphere");
	}

	private String getShapeDefinitionString(ParserRuleContext context, String keyword) {
		StringBuilder builder = new StringBuilder();
		builder.append(keyword).append(" ");
		
		builder.append(context.children.get(1).accept(this)).append(" ");
		return builder.toString();
	}

	@Override
	public String visitCubeDefinition(CubeDefinitionContext ctx) {
		return getShapeDefinitionString(ctx, "cube");
	}

	@Override
	public String visitCoordinates(CoordinatesContext ctx) {
		StringBuilder builder = new StringBuilder();
		for (ParseTree tree : ctx.children){
			builder.append(tree.accept(this)).append(" ");
		}
		return builder.toString();
	}

}
