package net.sourceforge.fenixedu.presentationTier.Action.internationalRelatOffice.candidacy.erasmus;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.Filtro.exception.FenixFilterException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.person.ChoosePersonBean;
import net.sourceforge.fenixedu.dataTransferObject.person.PersonBean;
import net.sourceforge.fenixedu.domain.CurricularCourse;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.candidacyProcess.IndividualCandidacyProcess;
import net.sourceforge.fenixedu.domain.candidacyProcess.erasmus.ErasmusCandidacyProcess;
import net.sourceforge.fenixedu.domain.candidacyProcess.erasmus.ErasmusIndividualCandidacyProcess;
import net.sourceforge.fenixedu.domain.candidacyProcess.erasmus.ErasmusIndividualCandidacyProcessBean;
import net.sourceforge.fenixedu.domain.caseHandling.Activity;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.person.IDDocumentType;
import net.sourceforge.fenixedu.injectionCode.AccessControl;
import net.sourceforge.fenixedu.presentationTier.Action.candidacy.IndividualCandidacyProcessDA;
import net.sourceforge.fenixedu.presentationTier.Action.candidacy.erasmus.DegreeCourseInformationBean;
import net.sourceforge.fenixedu.presentationTier.formbeans.FenixActionForm;
import net.sourceforge.fenixedu.util.StringUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/caseHandlingErasmusIndividualCandidacyProcess", module = "internationalRelatOffice", formBeanClass = FenixActionForm.class)
@Forwards( { @Forward(name = "intro", path = "/caseHandlingErasmusCandidacyProcess.do?method=listProcessAllowedActivities"),
	@Forward(name = "list-allowed-activities", path = "/candidacy/erasmus/listIndividualCandidacyActivities.jsp"),
	@Forward(name = "prepare-create-new-process", path = "/candidacy/erasmus/selectPersonForCandidacy.jsp"),
	@Forward(name = "fill-personal-information", path = "/candidacy/erasmus/fillPersonalInformation.jsp"),
	@Forward(name = "fill-candidacy-information", path = "/candidacy/erasmus/fillCandidacyInformation.jsp"),
	@Forward(name = "fill-degree-information", path = "/candidacy/erasmus/fillDegreeInformation.jsp"),
	@Forward(name = "fill-courses-information", path = "/candidacy/erasmus/fillCoursesInformation.jsp"),
	@Forward(name = "edit-candidacy-personal-information", path = "/candidacy/erasmus/editPersonalInformation.jsp"),
	@Forward(name = "edit-candidacy-information", path = "/candidacy/erasmus/editCandidacyInformation.jsp"),
	@Forward(name = "edit-degree-courses-information", path = "/candidacy/erasmus/editDegreeAndCoursesInformation.jsp"),
	@Forward(name = "set-gri-validation", path = "/internationalRelatOffice/candidacy/erasmus/setGriValidation.jsp"),
	@Forward(name = "visualize-alerts", path = "/candidacy/erasmus/visualizeAlerts.jsp"),
	@Forward(name = "prepare-edit-candidacy-documents", path = "/candidacy/erasmus/editCandidacyDocuments.jsp"),
	@Forward(name = "create-student-data", path = "/candidacy/erasmus/createStudentData.jsp"),
	@Forward(name = "view-student-data-username", path = "/candidacy/erasmus/viewStudentDataUsername.jsp"),
	@Forward(name = "edit-eidentifier", path = "/candidacy/erasmus/editEidentifier.jsp"),
	@Forward(name = "cancel-candidacy", path = "/candidacy/cancelCandidacy.jsp") })
public class ErasmusIndividualCandidacyProcessDA extends IndividualCandidacyProcessDA {

    @Override
    protected Class getParentProcessType() {
	return ErasmusCandidacyProcess.class;
    }

    @Override
    protected void prepareInformationForBindPersonToCandidacyOperation(HttpServletRequest request,
	    IndividualCandidacyProcess process) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void setStartInformation(ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getParentProcess(request));

	/*
	 * 06/05/2009 - Due to Public Candidacies, a candidacy created in admin
	 * office is external So we dont require ChoosePersonBean because a
	 * Person will not be associated or created at individual candidacy
	 * creation stage. Instead we bind with an empty PersonBean.
	 * 
	 * bean.setChoosePersonBean(new ChoosePersonBean());
	 */
	/*
	 * 21/07/2009 - Now we create a person to process the payments
	 * imediately
	 */
	bean.setChoosePersonBean(new ChoosePersonBean());
	bean.setPersonBean(new PersonBean());

	bean.getChoosePersonBean().setName("");
	bean.getChoosePersonBean().setDocumentType(IDDocumentType.FOREIGNER_IDENTITY_CARD);

	/*
	 * 06/05/2009 - Also we mark the bean as an external candidacy.
	 */
	bean.setInternalPersonCandidacy(Boolean.TRUE);
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);

    }

    @Override
    protected Class getProcessType() {
	return ErasmusIndividualCandidacyProcess.class;
    }

    @Override
    protected ErasmusCandidacyProcess getParentProcess(HttpServletRequest request) {
	return (ErasmusCandidacyProcess) super.getParentProcess(request);
    }

    @Override
    protected ErasmusIndividualCandidacyProcess getProcess(HttpServletRequest request) {
	return (ErasmusIndividualCandidacyProcess) super.getProcess(request);
    }

    @Override
    public ErasmusIndividualCandidacyProcessBean getIndividualCandidacyProcessBean() {
	return (ErasmusIndividualCandidacyProcessBean) super.getIndividualCandidacyProcessBean();
    }

    public ActionForward fillDegreeInformation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	request.setAttribute("degreeCourseInformationBean", new DegreeCourseInformationBean(
		(ExecutionYear) getIndividualCandidacyProcessBean().getCandidacyProcess().getCandidacyExecutionInterval()));

	return mapping.findForward("fill-degree-information");
    }

    public ActionForward fillDegreeInformationInvalid(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("fill-degree-information");
    }

    public ActionForward chooseDegree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	request.setAttribute("degreeCourseInformationBean", readDegreeCourseInformationBean(request));

	RenderUtils.invalidateViewState();

	if ("editCandidacy".equals(request.getAttribute("userAction"))) {
	    return mapping.findForward("edit-degree-courses-information");
	}

	return mapping.findForward("fill-degree-information");
    }

    private DegreeCourseInformationBean readDegreeCourseInformationBean(HttpServletRequest request) {
	return (DegreeCourseInformationBean) getRenderedObject("degree.course.information.bean");
    }

    public ActionForward addCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());

	ErasmusIndividualCandidacyProcessBean bean = (ErasmusIndividualCandidacyProcessBean) getIndividualCandidacyProcessBean();
	DegreeCourseInformationBean degreeCourseBean = readDegreeCourseInformationBean(request);

	if (degreeCourseBean.getChosenCourse() != null) {
	    bean.addCurricularCourse(degreeCourseBean.getChosenCourse());
	}

	request.setAttribute("degreeCourseInformationBean", readDegreeCourseInformationBean(request));

	RenderUtils.invalidateViewState();

	if ("editCandidacy".equals(request.getAttribute("userAction"))) {
	    return mapping.findForward("edit-degree-courses-information");
	}

	return mapping.findForward("fill-degree-information");
    }

    public ActionForward removeCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());

	ErasmusIndividualCandidacyProcessBean bean = (ErasmusIndividualCandidacyProcessBean) getIndividualCandidacyProcessBean();
	DegreeCourseInformationBean degreeCourseBean = readDegreeCourseInformationBean(request);

	CurricularCourse courseToRemove = getDomainObject(request, "removeCourseId");
	bean.removeCurricularCourse(courseToRemove);

	request.setAttribute("degreeCourseInformationBean", readDegreeCourseInformationBean(request));

	RenderUtils.invalidateViewState();

	if ("editCandidacy".equals(request.getAttribute("userAction"))) {
	    return mapping.findForward("edit-degree-courses-information");
	}

	return mapping.findForward("fill-degree-information");
    }

    public ActionForward prepareExecuteEditCandidacyPersonalInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {

	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	bean.setPersonBean(new PersonBean(getProcess(request).getPersonalDetails()));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-candidacy-personal-information");
    }

    public ActionForward executeEditCandidacyPersonalInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-candidacy-personal-information");
    }

    public ActionForward executeEditCandidacyPersonalInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {

	try {
	    executeActivity(getProcess(request), "EditCandidacyPersonalInformation", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-candidacy-personal-information");
	}
	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    public ActionForward prepareExecuteEditCandidacyInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-candidacy-information");
    }

    public ActionForward executeEditCandidacyInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    executeActivity(getProcess(request), "EditCandidacyInformation", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-candidacy-information");
	}
	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    public ActionForward executeEditCandidacyInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-candidacy-information");
    }

    public ActionForward prepareExecuteEditDegreeAndCoursesInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	request.setAttribute("degreeCourseInformationBean", new DegreeCourseInformationBean((ExecutionYear) getProcess(request)
		.getCandidacyProcess().getCandidacyExecutionInterval()));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-degree-courses-information");
    }

    public ActionForward executeEditDegreeAndCoursesInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    executeActivity(getProcess(request), "EditDegreeAndCoursesInformation", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-degree-courses-information");
	}
	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    public ActionForward executeEditDegreeAndCoursesInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-degree-courses-information");
    }

    @Override
    protected List<Activity> getAllowedActivities(final IndividualCandidacyProcess process) {
	List<Activity> activities = process.getAllowedActivities(AccessControl.getUserView());
	ArrayList<Activity> resultActivities = new ArrayList<Activity>();

	for (Activity activity : activities) {
	    if (activity.isVisibleForGriOffice()) {
		resultActivities.add(activity);
	    }
	}

	return resultActivities;
    }

    public ActionForward prepareExecuteSetGriValidation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));

	bean.setCreateAlert(true);
	bean.setSendEmail(true);

	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("set-gri-validation");

    }

    public ActionForward executeSetGriValidation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    ErasmusIndividualCandidacyProcessBean bean = (ErasmusIndividualCandidacyProcessBean) getIndividualCandidacyProcessBean();

	    if (bean.getCreateAlert()
		    && (StringUtils.isEmpty(bean.getAlertSubject()) || StringUtils.isEmpty(bean.getAlertBody()))) {
		addActionMessage(request, "error.erasmus.alert.subject.and.body.must.not.be.empty");
	    } else {
		executeActivity(getProcess(request), "SetGriValidation", getIndividualCandidacyProcessBean());
		return listProcessAllowedActivities(mapping, actionForm, request, response);
	    }
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	}

	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("set-gri-validation");
    }

    public ActionForward executeSetGriValidationInvalid(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("set-gri-validation");
    }

    public ActionForward prepareExecuteVisualizeAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));

	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("visualize-alerts");
    }

    public ActionForward prepareExecuteCreateStudentData(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);

	return mapping.findForward("create-student-data");
    }

    public ActionForward prepareExecuteCreateStudentDataInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("create-student-data");
    }

    public ActionForward executeCreateStudentData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());

	try {
	    executeActivity(getProcess(request), "CreateStudentData", getIndividualCandidacyProcessBean());
	    executeActivity(getProcess(request), "ImportToLDAP", getIndividualCandidacyProcessBean());

	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("create-student-data");
	}

	return mapping.findForward("view-student-data-username");
    }

    public ActionForward prepareExecuteSetEIdentifierForTesting(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	
	bean.setPersonBean(new PersonBean(getProcess(request).getPersonalDetails()));

	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);

	return mapping.findForward("edit-eidentifier");
    }

    public ActionForward prepareExecuteSetEIdentifierForTestingInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);

	return mapping.findForward("edit-eidentifier");
    }

    public ActionForward executeSetEIdentifierForTesting(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	final ErasmusIndividualCandidacyProcessBean bean = new ErasmusIndividualCandidacyProcessBean(getProcess(request));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);

	try {
	    executeActivity(getProcess(request), "SetEIdentifierForTesting", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-eidentifier");
	}

	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

}
