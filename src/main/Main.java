package main;

import java.util.Scanner;

import chess.Position;
import chess.UndoInfo;
import engine.Evaluator;
import engine.MoveGenerator;
import engine.MoveGenerator.MoveList;
import engine.Searcher;
import gui.ChessFrame;

public class Main {

	public static void main(String[] args) {
		Position position = new Position();
		position.setPosition("rnbqkbnr/ppp1pppp/3p4/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1");

		boolean debug = true;

		if (debug) {
			debugPosition(position);
		}
//		position.setPosition(Position.POSITION_2);
//		position.setPosition("8/8/3Pp3/8/8/8/8/8 w - e7 0 1");
//		position.drawPieces();

		new ChessFrame(); // display game screen

	}

	private static void debugPosition(Position position) {
		Scanner sc = new Scanner(System.in);

		position.setPosition("rnbqkbnr/ppp1pppp/3p4/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1");
		position.drawBoard();
		System.out.println(Evaluator.evaluate(position));
		Searcher searcher = new Searcher(position);
		searcher.search(1);

		MoveList moves = MoveGenerator.generateAllMoves(position);
		UndoInfo ui = new UndoInfo();
		for (int mi = 0; mi < moves.moveCount; mi++) {
			int move = moves.mvs[mi];
			if (!position.makeMove(move, ui)) {
				continue;
			}
			sc.nextLine();
			position.drawBoard();
			System.out.println(Evaluator.evaluate(position));
			searcher.search(1);
			System.out.println(position.getFenString());

			sc.nextLine();
			position.unMakeMove(ui);
			position.drawBoard();
			System.out.println(Evaluator.evaluate(position));
		}
		sc.close();
	}

}
