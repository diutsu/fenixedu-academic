package net.sourceforge.fenixedu.presentationTier.Action.research;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.applicationTier.Filtro.exception.FenixFilterException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.domain.Language;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.organizationalStructure.Party;
import net.sourceforge.fenixedu.domain.research.ResearchInterest;
import net.sourceforge.fenixedu.domain.research.ResearchInterest.ResearchInterestTranslation;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;
import net.sourceforge.fenixedu.presentationTier.Action.sop.utils.ServiceUtils;
import net.sourceforge.fenixedu.renderers.model.CreationMetaObject;
import net.sourceforge.fenixedu.renderers.utils.RenderUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InterestsManagementDispatchAction extends FenixDispatchAction {

    private static final int UP = -1;

    private static final int DOWN = 1;

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Integer oid = Integer.parseInt(request.getParameter("oid"));
        IUserView userView = getUserView(request);

        ServiceUtils.executeService(userView, "DeleteResearchInterest", new Object[] { oid });

        return prepare(mapping, form, request, response);
    }

    public ActionForward up(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        changeOrder(request, UP);
        return prepare(mapping, form, request, response);
    }

    public ActionForward down(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        changeOrder(request, DOWN);
        return prepare(mapping, form, request, response);
    }

    public ActionForward prepare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<ResearchInterest> orderedInterests = getOrderedInterests(request);                    
        RenderUtils.invalidateViewState();
        request.setAttribute("researchInterests", orderedInterests);
        
        return mapping.findForward("Success");
    }

    public ActionForward prepareInsertInterest (ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        List<ResearchInterest> orderedInterests = getOrderedInterests(request);

        if (orderedInterests.size() > 0) {
            request.setAttribute("lastOrder", orderedInterests.get(orderedInterests.size() - 1)
                    .getOrder() + 1);
        } else {
            request.setAttribute("lastOrder", 1);
        }
               
        request.setAttribute("party", (Party) getUserView(request).getPerson());

        return mapping.findForward("InsertNewInterest");        
    }
    
    public ActionForward manageTranslations(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        final Integer oid = Integer.parseInt(request.getParameter("oid"));
        final ResearchInterest researchInterest = rootDomainObject.readResearchInterestByOID(oid);

        if (RenderUtils.getViewState() != null) {
            final ResearchInterestTranslation translation = (ResearchInterestTranslation) ((CreationMetaObject) RenderUtils
                    .getViewState().getMetaObject()).getCreatedObject();
            researchInterest.addTranslation(translation);
        }

        request.setAttribute("interestId", oid);
        request.setAttribute("interestTranslations", researchInterest.getAllTranslations());

        RenderUtils.invalidateViewState();

        return mapping.findForward("ManageTranslations");
    }

    public ActionForward deleteTranslation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
      
        final Integer oid = Integer.parseInt(request.getParameter("oid"));
        final Language language = Language.valueOf(request.getParameter("language"));

        final ResearchInterest researchInterest = rootDomainObject.readResearchInterestByOID(oid);

        researchInterest.removeTranslation(language);

        return manageTranslations(mapping, form, request, response);
    }

    private void changeOrder(HttpServletRequest request, int direction) throws FenixFilterException,
            FenixServiceException {
        Integer oid = Integer.parseInt(request.getParameter("oid"));

        IUserView userView = getUserView(request);
        Person person = userView.getPerson();

        ResearchInterest interest = rootDomainObject.readResearchInterestByOID(oid);
        List<ResearchInterest> orderedInterests = getOrderedInterests(request);

        int index = orderedInterests.indexOf(interest);
        if (index + direction >= 0) {
            orderedInterests.remove(interest);
            if (index + direction > orderedInterests.size()) {
                orderedInterests.add(index, interest);
            } else {
                orderedInterests.add(index + direction, interest);
            }

            ServiceUtils.executeService(userView, "ChangeResearchInterestOrder", new Object[] { person,
                    orderedInterests });
        }
    }

    private List<ResearchInterest> getOrderedInterests(HttpServletRequest request)
            throws FenixFilterException, FenixServiceException {
       
        List<ResearchInterest> researchInterests = getUserView(request).getPerson()
                .getResearchInterests();

        List<ResearchInterest> orderedInterests = new ArrayList<ResearchInterest>(researchInterests);
        Collections.sort(orderedInterests, new Comparator<ResearchInterest>() {
            public int compare(ResearchInterest researchInterest1, ResearchInterest researchInterest2) {
                return researchInterest1.getOrder().compareTo(researchInterest2.getOrder());
            }
        });
        return orderedInterests;
    }

}