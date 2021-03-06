package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Park;
import entity.VisitorReport;
import gui.DepartmentManagerVisitionReportController;
import gui.IClientServerCommunication;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import message.ClientMessage;
import message.ServerMessage;

class DepartmentManagerVisitionReportControllerTest {
	JFXPanel panel = new JFXPanel();
	DepartmentManagerVisitionReportController departmentManagerVisitionReportController;
	IClientServerCommunication clientServerCommunicationStub;
	Object returnValFromServer;
	Object parksResponse;
	Object park1Data;
	Object park2Data, park3Data;
	Exception throwen = null;
	ArrayList<Park> parkList = new ArrayList<>();

	class ClientServerCommunicationStub implements IClientServerCommunication {

		@Override
		public void popUpError(String msg) {
		}

		@Override
		public void sendToServer(Object msg) {

			ClientMessage clientMsg = (ClientMessage) msg;
			switch (clientMsg.getType()) {
			case GET_PARKS:
				returnValFromServer = parksResponse;
				break;
			case DEP_MNG_VISITION_REPORT:
				String parkName = (String) clientMsg.getMessage();
				switch (parkName) {
				case "1":
					returnValFromServer = park1Data;
					break;
				case "2":
					returnValFromServer = park2Data;
					break;
				case "3":
					returnValFromServer = park3Data;
					break;
				default:
					throw new IllegalArgumentException("Got " + parkName + " instead of the park name");
				}
				break;
			default:
				throw new IllegalArgumentException(
						"only \"GET_PARKS\" and \"DEP_MNG_VISITION_REPORT\" accepted but got " + clientMsg.getType());
			}
		}

		@Override
		public ServerMessage getServerMsg() {
			return new ServerMessage(null, returnValFromServer);
		}

		@Override
		public void setUser(Object user) {
		}

	}

	@BeforeEach
	void setUp() throws Exception {
		clientServerCommunicationStub = new ClientServerCommunicationStub();
		DepartmentManagerVisitionReportController.setClientServerCommunication(clientServerCommunicationStub);
		parkList.add(new Park("1", 0, 0, 0, 0));
		parkList.add(new Park("2", 0, 0, 0, 0));
		parkList.add(new Park("3", 0, 0, 0, 0));
		parksResponse = parkList;
		throwen = null;
	}

	private DepartmentManagerVisitionReportController getScreen() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/DepartmentManagerVisitionReport.fxml"));
		try {
			loader.load();

			return loader.getController();
		} catch (IOException e) {
			throwen = e;
		}
		return null;
	}

	/*
	 * check the amount of serials for arrival time: 8. input: 3 serials for arrival
	 * time: 8. expected: 3 serials. result: 3 serials.
	 */
	@Test
	void serialsForXValuesTest() {
		Map<Integer, VisitorReport> mapDataPark1 = new LinkedHashMap<Integer, VisitorReport>();
		mapDataPark1.put(8, new VisitorReport(8));
		mapDataPark1.get(8).setAvgGuid(5.0);
		mapDataPark1.get(8).setAvgRegular(6.0);
		mapDataPark1.get(8).setAvgSubscriber(7.0);
		park1Data = mapDataPark1;
		park2Data = mapDataPark1;
		park3Data = mapDataPark1;
		DepartmentManagerVisitionReportController cont = getScreen();

		if (!(cont.barPark1.getData().get(0).getData().get(0).getXValue().equals("8:00"))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(1).getData().get(0).getXValue().equals("8:00"))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(2).getData().get(0).getXValue().equals("8:00"))) {
			fail();
		}
		assertTrue(true);
	}

	/*
	 * check the average stay of serials for arrival time: 8. input: map with the
	 * values- subscriber serial= 7.0, group serial = 5.0, individual serial=6.0 in
	 * arrival time: 8. excepted : subscriber serial= 7.0, group serial = 5.0,
	 * individual serial=6.0 in the varChar.
	 */
	@Test
	void serialsForYValuesTest() {
		Map<Integer, VisitorReport> mapDataPark1 = new LinkedHashMap<Integer, VisitorReport>();
		mapDataPark1.put(8, new VisitorReport(8));
		mapDataPark1.get(8).setAvgGuid(5.0);
		mapDataPark1.get(8).setAvgRegular(6.0);
		mapDataPark1.get(8).setAvgSubscriber(7.0);
		park1Data = mapDataPark1;
		park2Data = mapDataPark1;
		park3Data = mapDataPark1;
		DepartmentManagerVisitionReportController cont = getScreen();

		if (!(cont.barPark1.getData().get(0).getData().get(0).getYValue().equals(7.0))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(1).getData().get(0).getYValue().equals(5.0))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(2).getData().get(0).getYValue().equals(6.0))) {
			fail();
		}
		assertTrue(true);
	}

	/*
	 * check the average stay of serials for arrival time: 8. input: map with the
	 * values- subscriber serial= 7.0, group serial = 5.0, in arrival time: 8.
	 * excepted : subscriber serial= 7.0, group serial = 5.0, in the varChar.
	 */
	@Test
	void oneSerialEmptyTest() {

		Map<Integer, VisitorReport> mapDataPark1 = new LinkedHashMap<Integer, VisitorReport>();
		mapDataPark1.put(8, new VisitorReport(8));
		mapDataPark1.get(8).setAvgGuid(5.0);
		mapDataPark1.get(8).setAvgSubscriber(7.0);
		park1Data = mapDataPark1;
		park2Data = mapDataPark1;
		park3Data = mapDataPark1;
		DepartmentManagerVisitionReportController cont = getScreen();

		if (!(cont.barPark1.getData().get(0).getData().get(0).getYValue().equals(7.0))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(1).getData().get(0).getYValue().equals(5.0))) {
			fail();
		}
		if (!(cont.barPark1.getData().get(2).getData().get(0).getYValue().equals(0.0))) {
			fail();
		}
		assertTrue(true);
	}

	/*
	 * check if the name label identify to the park input: 1 excepted: 1
	 */
	@Test
	void existingParkNameTest() {

		Map<Integer, VisitorReport> mapDataPark1 = new LinkedHashMap<Integer, VisitorReport>();
		mapDataPark1.put(8, new VisitorReport(8));
		mapDataPark1.get(8).setAvgGuid(5.0);
		mapDataPark1.get(8).setAvgRegular(6.0);
		mapDataPark1.get(8).setAvgSubscriber(7.0);
		park1Data = mapDataPark1;
		park2Data = mapDataPark1;
		park3Data = mapDataPark1;
		DepartmentManagerVisitionReportController cont = getScreen();

		if (!(cont.park1.getText().equals("1"))) {
			fail();
		}
		assertTrue(true);
	}

	/*
	 * input for park1Data : null excepted : NullPointerException result =
	 * NullPointerException
	 */
	@Test
	void dataParksIsNullTest() {
		park1Data = null;
		park2Data = null;
		park3Data = null;

		DepartmentManagerVisitionReportController cont = getScreen();
		if (throwen == null) {
			fail();
		}
		assertTrue(true);
	}

	/*
	 * input for park1Data : null excepted : NullPointerException result =
	 * NullPointerException
	 */
	@Test
	void parksResponseIsNullTest() {
		parksResponse = null;

		DepartmentManagerVisitionReportController cont = getScreen();
		if (throwen == null) {
			fail();
		}
		assertTrue(true);
	}
}
