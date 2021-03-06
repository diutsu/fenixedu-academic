<%--

    Copyright © 2002 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html:xhtml />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

	<em><bean:message key="label.payments" bundle="ACADEMIC_OFFICE_RESOURCES"/></em>
	<h2><bean:message bundle="ACADEMIC_OFFICE_RESOURCES" key="label.payments.exemptions" /></h2>
	<br />

	<logic:messagesPresent message="true" property="context">
		<ul>
			<html:messages id="messages" message="true" bundle="ACADEMIC_OFFICE_RESOURCES" property="context">
				<li><span class="error0"><bean:write bundle="ACADEMIC_OFFICE_RESOURCES" name="messages" /></span></li>
			</html:messages>
		</ul>
		<br />
	</logic:messagesPresent>

	<logic:messagesPresent message="true" property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>">
		<ul>
			<html:messages id="messages" message="true" bundle="ACADEMIC_OFFICE_RESOURCES" property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>">
				<li><span class="error0"><bean:write name="messages" /></span></li>
			</html:messages>
		</ul>
		<br />
	</logic:messagesPresent>

	<fr:hasMessages for="exemptionBean" type="conversion">
		<ul>
			<fr:messages>
				<li><span class="error0"><fr:message /></span></li>
			</fr:messages>
		</ul>
	</fr:hasMessages>

	<bean:define id="event" name="exemptionBean" property="event" />
	<bean:define id="person" name="event" property="person" />

	<strong><bean:message bundle="ACADEMIC_OFFICE_RESOURCES" key="label.payments.person" />:</strong>
	<fr:view name="person" schema="person.view-with-name-and-idDocumentType-and-documentIdNumber">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle4" />
		</fr:layout>
	</fr:view>

	<br />

	<bean:define id="eventId" name="event" property="externalId" />

	<fr:edit id="exemptionBean" name="exemptionBean" action="<%="/exemptionsManagement.do?method=createPhdEventExemption&eventId="+eventId%>">
		<fr:schema bundle="PHD_RESOURCES" type="org.fenixedu.academic.ui.struts.action.phd.PhdEventExemptionBean">
			<fr:slot name="justificationType" layout="menu-postback" required="true">
				<fr:property name="providerClass" value="org.fenixedu.academic.ui.struts.action.phd.PhdEventExemptionJustificationTypeForGratuityProvider" />
				<fr:property name="saveOptions" value="true"/>
				<fr:property name="defaultOptionHidden" value="true" />
			</fr:slot>

			<logic:equal name="exemptionBean" property="justificationType" value="DIRECTIVE_COUNCIL_AUTHORIZATION">
				<fr:slot name="dispatchDate" required="true" />
				<fr:slot name="value" required="true" />
				<fr:slot name="reason" layout="longText" required="true">
					<fr:property name="rows" value="4" />
					<fr:property name="columns" value="40" />
				</fr:slot>
			</logic:equal>

			<logic:equal name="exemptionBean" property="justificationType" value="THIRD_PARTY_CONTRIBUTION">
				<fr:slot name="creditorSocialSecurityNumber" layout="autoComplete"
						 validator="org.fenixedu.academic.ui.renderers.validators.RequiredAutoCompleteSelectionValidator"
						 key="label.payments.event.creditor.party" bundle="ACADEMIC_OFFICE_RESOURCES">
					<fr:property name="size" value="70"/>
					<fr:property name="format" value="\${party.socialSecurityNumber} - \${party.name}"/>
					<fr:property name="indicatorShown" value="true"/>
					<fr:property name="provider"
								 value="org.fenixedu.academic.service.services.commons.searchers.SearchExternalScholarshipPartySocialSecurityNumber"/>
					<fr:property name="args" value="slot=socialSecurityNumber"/>
					<fr:property name="minChars" value="1"/>
					<fr:property name="maxCount" value="20"/>
				</fr:slot>
				<fr:slot name="file" key="label.payments.event.creditor.document" bundle="ACADEMIC_OFFICE_RESOURCES">
					<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.FileValidator">
						<fr:property name="required" value="true"/>
						<fr:property name="typeMessage" value="label.payments.event.creditor.document.type"/>
						<fr:property name="bundle" value="ACADEMIC_OFFICE_RESOURCES"/>
						<fr:property name="acceptedTypes" value="application/pdf"/>
					</fr:validator>
					<fr:property name="size" value="70"/>
					<fr:property name="fileNameSlot" value="fileName"/>
					<fr:property name="fileSizeSlot" value="fileSize"/>
				</fr:slot>
				<fr:slot name="reason" key="label.payments.event.transfer.reason"/>
			</logic:equal>

			<logic:equal name="exemptionBean" property="justificationType" value="PHD_GRATUITY_FCT_SCHOLARSHIP_EXEMPTION">
				<fr:slot name="creditorSocialSecurityNumber" layout="autoComplete"
						 validator="org.fenixedu.academic.ui.renderers.validators.RequiredAutoCompleteSelectionValidator"
						 key="label.payments.event.creditor.party">
					<fr:property name="size" value="70"/>
					<fr:property name="format" value="\${party.socialSecurityNumber} - \${party.name}"/>
					<fr:property name="indicatorShown" value="true"/>
					<fr:property name="provider"
								 value="org.fenixedu.academic.service.services.commons.searchers.SearchExternalScholarshipPartySocialSecurityNumber"/>
					<fr:property name="args" value="slot=socialSecurityNumber"/>
					<fr:property name="minChars" value="1"/>
					<fr:property name="maxCount" value="20"/>
				</fr:slot>
				<fr:slot name="value" required="true" />
			</logic:equal>

			<logic:equal name="exemptionBean" property="justificationType" value="FINE_EXEMPTION">
				<fr:slot name="reason" layout="longText" required="true">
					<fr:property name="rows" value="4" />
					<fr:property name="columns" value="40" />
				</fr:slot>
			</logic:equal>

		</fr:schema>

		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle4" />
			<fr:property name="columnClasses" value=",,tdclear tderror1" />
		</fr:layout>

		<fr:destination name="invalid" path='<%="/exemptionsManagement.do?method=prepareCreatePhdEventExemptionInvalid&amp;eventId=" + eventId %>'/>
		<fr:destination name="postback" path='<%= "/exemptionsManagement.do?method=changeForm&amp;eventId=" + eventId %>'/>
		<fr:destination name="cancel" path="<%="/exemptionsManagement.do?method=showExemptions&eventId=" + eventId%>"/>
	</fr:edit>
