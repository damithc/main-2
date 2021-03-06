//@@author a0124946

package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class LogicMainClearTest {
	LogicMain logicTest;
	
	@Before
	public void init(){
		logicTest = new LogicMain();
		String userInput6 = "clear";
		String testOutput = logicTest.processCommand(userInput6);
	}
	
	@Test
	public void testClearing(){
		String userInput1 = "add machine gun noise during livestream";
		String userInput2 = "add VIP room with only plastic chairs";
		String userInput3 = "add 60hz samsung monitors instead of 144hz";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors";
		String userInput5 = "add glue and cardboard to construct soundproof rooms";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput6 = "clear";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput, expectedOutput);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testClearingWithDates(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput6 = "clear";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput, expectedOutput);
		assertEquals(testList.size(), 0);
	}	
	
	@Test
	public void testClearingWhitespaceBefore(){
		String userInput6 = "    clear";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Invalid command";
		assertEquals(testOutput, expectedOutput);
	}
	
	@Test
	public void testClearingWhitespaceAfter(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput6 = "clear      ";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput, expectedOutput);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testClearingWithNumbers(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		String userInput6 = "clear 1";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput, expectedOutput);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testClearingWithDateTime(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);

		String userInput6 = "clear 1pm";
		String testOutput = logicTest.processCommand(userInput6);
		String expectedOutput = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput, expectedOutput);
		assertEquals(testList.size(), 0);
	}
	@Test
	public void testClearingWithDateTime2(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput7 = "clear 1pm 2pm";
		String testOutput2 = logicTest.processCommand(userInput7);
		String expectedOutput2 = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput2, expectedOutput2);
		assertEquals(testList.size(), 0);
	}
	@Test
	public void testClearingWithDateTime3(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput8 = "clear 1pm 1feb";
		String testOutput3 = logicTest.processCommand(userInput8);
		String expectedOutput3 = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput3, expectedOutput3);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testClearingWithDateTime4(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput9 = "clear 1feb";
		String testOutput4 = logicTest.processCommand(userInput9);
		String expectedOutput4 = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput4, expectedOutput4);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testClearingWithDateTime5(){
		String userInput1 = "add machine gun noise during livestream 1pm";
		String userInput2 = "add VIP room with only plastic chairs 10feb";
		String userInput3 = "add 60hz samsung monitors instead of 144hz 10feb 11feb";
		String userInput4 = "add samsung stickers onto valve's 144hz benq monitors 10feb 2pm 11feb 1pm";
		String userInput5 = "add glue and cardboard to construct soundproof rooms 1pm 2pm";
	
		logicTest.processCommand(userInput1);
		logicTest.processCommand(userInput2);
		logicTest.processCommand(userInput3);
		logicTest.processCommand(userInput4);
		logicTest.processCommand(userInput5);
		ArrayList<Task> testList = logicTest.getOperatingTasksForUI();
		assertEquals(testList.size(), 5);
		
		String userInput10 = "clear 1feb 2feb 1pm 2pm";
		String testOutput4 = logicTest.processCommand(userInput10);
		String expectedOutput4 = "Your dashboard has been cleared.";
		System.out.println(testList.size());
		assertEquals(testOutput4, expectedOutput4);
		assertEquals(testList.size(), 0);
	}
	
}
