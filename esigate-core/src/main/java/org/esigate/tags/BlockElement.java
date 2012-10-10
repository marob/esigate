package org.esigate.tags;

import java.io.IOException;

import org.esigate.HttpErrorPage;
import org.esigate.aggregator.AggregationSyntaxException;
import org.esigate.parser.Element;
import org.esigate.parser.ElementType;
import org.esigate.parser.ParserContext;


class BlockElement implements Element {
	public final static ElementType TYPE = new ElementType() {

		public boolean isStartTag(String tag) {
			return tag.startsWith("<!--$beginblock$");
		}

		public boolean isEndTag(String tag) {
			return tag.startsWith("<!--$endblock$");
		}

		public Element newInstance() {
			return new BlockElement();
		}

	};

	private BlockRenderer blockRenderer;
	private boolean nameMatches;

	public boolean onError(Exception e, ParserContext ctx) {
		return false;
	}

	public void onTagEnd(String tag, ParserContext ctx) {
		// Stop writing
		if (nameMatches) {
			blockRenderer.setWrite(false);
		}
	}

	public void onTagStart(String tag, ParserContext ctx) throws IOException, HttpErrorPage {
		String[] parameters = tag.split("\\$");
		if (parameters.length != 4) {
			throw new AggregationSyntaxException("Invalid syntax: " + tag);
		}
		String name = parameters[2];
		this.blockRenderer = ctx.findAncestor(BlockRenderer.class);
		// If name matches, start writing
		nameMatches = name.equals(blockRenderer.getName());
		if (nameMatches) {
			blockRenderer.setWrite(true);
		}
	}

	public boolean isClosed() {
		return false;
	}

	public void characters(CharSequence csq, int start, int end) throws IOException {
		blockRenderer.append(csq, start, end);
	}

}