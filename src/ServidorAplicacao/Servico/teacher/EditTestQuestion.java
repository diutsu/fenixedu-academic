/*
 * Created on 6/Ago/2003
 *
 */
package ServidorAplicacao.Servico.teacher;

import java.util.Iterator;
import java.util.List;

import Dominio.ITest;
import Dominio.ITestQuestion;
import Dominio.Test;
import Dominio.TestQuestion;
import ServidorAplicacao.IServico;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.InvalidArgumentsServiceException;
import ServidorPersistente.ExcepcaoPersistencia;
import ServidorPersistente.IPersistentTest;
import ServidorPersistente.IPersistentTestQuestion;
import ServidorPersistente.ISuportePersistente;
import ServidorPersistente.OJB.SuportePersistenteOJB;

/**
 * @author Susana Fernandes
 */
public class EditTestQuestion implements IServico {

	private static EditTestQuestion service = new EditTestQuestion();

	public static EditTestQuestion getService() {
		return service;
	}

	public EditTestQuestion() {
	}

	public String getNome() {
		return "EditTestQuestion";
	}

	public boolean run(
		Integer executionCourseId,
		Integer testId,
		Integer testQuestionId,
		Integer questionOrder,
		Integer questionValue)
		throws FenixServiceException {
		try {
			ISuportePersistente persistentSuport =
				SuportePersistenteOJB.getInstance();
			IPersistentTestQuestion persistentTestQuestion =
				persistentSuport.getIPersistentTestQuestion();
			ITestQuestion testQuestion = new TestQuestion(testQuestionId);
			testQuestion =
				(ITestQuestion) persistentTestQuestion.readByOId(
					testQuestion,
					true);
			if (testQuestion == null)
				throw new InvalidArgumentsServiceException();
			testQuestion.setTestQuestionValue(questionValue);
			if (!questionOrder.equals(new Integer(-2))) {
				IPersistentTest persistentTest =
					persistentSuport.getIPersistentTest();
				ITest test = new Test(testId);
				test = (ITest) persistentTest.readByOId(test, false);
				List testQuestionList = persistentTestQuestion.readByTest(test);

				if (questionOrder.equals(new Integer(-1)))
					questionOrder = new Integer(testQuestionList.size());

				Iterator it = testQuestionList.iterator();
				Integer questionOrderOld = testQuestion.getTestQuestionOrder();
				if (questionOrder.compareTo(questionOrderOld) < 0) {
					questionOrder = new Integer(questionOrder.intValue() + 1);
					while (it.hasNext()) {
						ITestQuestion testQuestionIt = (TestQuestion) it.next();
						Integer questionOrderIt =
							testQuestionIt.getTestQuestionOrder();

						if (questionOrderIt.compareTo(questionOrder) >= 0
							&& questionOrderIt.compareTo(questionOrderOld) < 0) {
							persistentTestQuestion.lockWrite(testQuestionIt);
							testQuestionIt.setTestQuestionOrder(
								new Integer(questionOrderIt.intValue() + 1));
						}
					}
				} else if (questionOrder.compareTo(questionOrderOld) > 0) {
					while (it.hasNext()) {
						ITestQuestion testQuestionIt = (TestQuestion) it.next();
						Integer questionOrderIt =
							testQuestionIt.getTestQuestionOrder();

						if (questionOrderIt.compareTo(questionOrder) <= 0
							&& questionOrderIt.compareTo(questionOrderOld) > 0) {
							persistentTestQuestion.lockWrite(testQuestionIt);
							testQuestionIt.setTestQuestionOrder(
								new Integer(questionOrderIt.intValue() - 1));
						}
					}
				}
				testQuestion.setTestQuestionOrder(questionOrder);
			}
			return true;
		} catch (ExcepcaoPersistencia e) {
			throw new FenixServiceException(e);
		}
	}
}