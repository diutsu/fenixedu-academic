<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %>
<h2>
	<bean:message key="message.student.shift.enrollment" />
</h2>
<span class="error"><html:errors/></span>
<logic:notPresent name="infoShiftEnrollment" >
	<span class="error"><bean:message key="error.notAuthorized.ShiftEnrollment" /></span>
</logic:notPresent>
<logic:present name="infoShiftEnrollment" >
	<bean:define id="executionDegreeId" name="infoShiftEnrollment" property="infoExecutionDegree.idInternal" />
	<bean:define id="studentId" name="infoShiftEnrollment" property="infoStudent.idInternal" />

	<table width="60%">
		<tr>
			<td class="infoop" colspan='6'>						
				<br />
				<ul>
					<li>
						<bean:define id="link">
							<bean:message key="link.shift.enrolement.edit"/>
						</bean:define>
						<html:link page="<%="/studentShiftEnrollmentManagerLoockup.do?method=" + link + "&amp;studentId=" + pageContext.findAttribute("studentId").toString()%>">
					  		<b><bean:message key="link.shift.enrolment" />/<bean:write name="link"/></b>
						</html:link>
						&nbsp;-&nbsp;<bean:message key="message.shift.enrollment.modifyShifts.help" />
					</li>
					<li>
						<html:link page="/studentTimeTable.do" target="_blank" >
							<b><bean:message key="link.my.timetable" /></b>
						</html:link>
						&nbsp;-&nbsp;<bean:message key="message.shift.enrollment.timetable.help" />
					</li>
					<li>
						<html:link href="<%= request.getContextPath() + "/dotIstPortal.do?prefix=/student&amp;page=/index.do" %>">
							<b><bean:message key="button.student.end" /></b>
						</html:link>
						&nbsp;-&nbsp;<bean:message key="message.shift.enrollment.over.help" />
					</li>
				</ul>
				<br/>
					Se ainda n�o se inscreveu nas disciplinas, escolha	<html:link page="<%= "/warningFirst.do"%>">
							<b><bean:message key="link.student.enrollment" /></b>
						</html:link>
		
				
				
			</td>
		</tr>

		<br/><br/>
		<logic:notEmpty name="infoEnrolledNewShiftEnrollmentList">
			<tr>
				<td><bean:message key="label.curricular.course.name"/></td>
				<td><bean:message key="label.enrollment.state"/></td>
				<td><bean:message key="label.shiftType.theoric"/></td>
				<td><bean:message key="label.shiftType.pratic"/></td>
				<td><bean:message key="label.shiftType.laboratory"/></td>
				<td><bean:message key="label.shiftType.theoricoPratic"/></td>
			</tr>			
			<logic:iterate id="infoNewShiftEnrollment" name="infoEnrolledNewShiftEnrollmentList" 
					type="net.sourceforge.fenixedu.dataTransferObject.InfoNewShiftEnrollment">
				<tr>
					<td><bean:write name="infoNewShiftEnrollment" property="infoExecutionCourse.nome"/></td>
					<bean:define id="condition" name="infoNewShiftEnrollment" property="enrollmentState"/>					
					<td><bean:message key='<%=condition.toString()%>' bundle="ENUMERATION_RESOURCES"/></td>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="theoricType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="theoricShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="theoricShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="theoricType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="praticType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="praticShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="praticShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="praticType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="laboratoryType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="laboratoryShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="laboratoryShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="laboratoryType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="theoricoPraticType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="theoricoPraticShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="theoricoPraticShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="theoricoPraticType">
						<td></td>
					</logic:empty>																				
				</tr>														
			</logic:iterate>
		</logic:notEmpty>
		
		<logic:notEmpty name="infoNotEnrolledNewShiftEnrollmentList">
			<tr>
				<td><bean:message key="label.curricular.course.name"/></td>
				<td><bean:message key="label.shiftType.theoric"/></td>
				<td><bean:message key="label.shiftType.pratic"/></td>
				<td><bean:message key="label.shiftType.laboratory"/></td>
				<td><bean:message key="label.shiftType.theoricoPratic"/></td>
			</tr>			
			<logic:iterate id="infoNewShiftEnrollment" name="infoNotEnrolledNewShiftEnrollmentList" 
					type="net.sourceforge.fenixedu.dataTransferObject.InfoNewShiftEnrollment">
				<tr>
					<td><bean:write name="infoNewShiftEnrollment" property="infoExecutionCourse.nome"/></td>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="theoricType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="theoricShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="theoricShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="theoricType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="praticType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="praticShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="praticShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="praticType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="laboratoryType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="laboratoryShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="laboratoryShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="laboratoryType">
						<td></td>
					</logic:empty>
					
					<logic:notEmpty name="infoNewShiftEnrollment" property="theoricoPraticType">
						<logic:notEmpty name="infoNewShiftEnrollment" property="theoricoPraticShift">
							<td><bean:message key="label.shift.enrolled"/></td>
						</logic:notEmpty>
						<logic:empty name="infoNewShiftEnrollment" property="theoricoPraticShift">
							<td><html:link page=""><bean:message key="label.shift.toEnroll"/></html:link></td>
						</logic:empty>
					</logic:notEmpty>
					<logic:empty name="infoNewShiftEnrollment" property="theoricoPraticType">
						<td></td>
					</logic:empty>																				
				</tr>														
			</logic:iterate>
		</logic:notEmpty>		
		
		<logic:present name="infoShiftEnrollment" property="infoShiftEnrollment">
			<tr>
				<td colspan='6'>
					<br />
					<bean:define id="numberCourseWithShiftEnrollment" name="infoShiftEnrollment" property="numberCourseWithShiftEnrollment" />
					<logic:lessEqual name="numberCourseWithShiftEnrollment" value="0">
						<p><strong><bean:message key="message.student.shiftEnrollment.confirmation" /></strong></p>
					</logic:lessEqual>
					
					<logic:greaterThan  name="numberCourseWithShiftEnrollment" value="0">
						<p><span class="error"><bean:message key="message.student.shiftEnrollment.lacksCourses" arg0="<%= numberCourseWithShiftEnrollment.toString()%>"/></span></p>
					</logic:greaterThan >
				</td>
			</tr>
			<bean:define id="elem" value="" type="java.lang.String"/>
			<logic:iterate id="infoShift" name="infoShiftEnrollment" property="infoShiftEnrollment" type="net.sourceforge.fenixedu.dataTransferObject.InfoShift">
				<%-- COURSES --%>
				<logic:present name="elem">
					<logic:notEqual name="elem" value="<%=infoShift.getInfoDisciplinaExecucao().getNome()%>">
						<tr>
							<td colspan='6'>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>	
						<tr>
							<td class="listClasses-subheader" style="text-align:left;background:#4F82B5" colspan='6' >
								<bean:write name="infoShift" property="infoDisciplinaExecucao.nome" />
							</td>
						</tr>	
					</logic:notEqual>
				</logic:present>
				<%-- SHIFTS --%>
				<tr>
					<td class="listClasses-header" style="text-align:left" colspan='6'>
						<bean:message key="property.turno" />:</b>&nbsp;
						<bean:write name="infoShift" property="nome" />
						<bean:define id="infoShiftId" name="infoShift" property="idInternal" />
						-
						<html:link page="<%= "/studentShiftEnrollmentManager.do?method=unEnroleStudentFromShift&amp;studentId="
													+ pageContext.findAttribute("studentId").toString()
													+ "&amp;shiftId="
													+ pageContext.findAttribute("infoShiftId").toString()
													%>">
							<bean:message key="link.unenrole.shift" />
						</html:link>
					</th>
				</tr>

				<%-- LESSONS --%>
				<logic:iterate id="infoLesson" name="infoShift" property="infoLessons">
					<tr>
						<td style="text-align:center">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td style="text-align:center">
							<bean:write name="infoLesson" property="tipo" />								
						</td>
						<td style="text-align:center">
							<bean:write name="infoLesson" property="diaSemana" />
						</td>
						<td style="text-align:center">
							<dt:format pattern="HH:mm">
								<bean:write name="infoLesson" property="inicio.timeInMillis" />
							</dt:format>								
							&nbsp;-&nbsp;
							<dt:format pattern="HH:mm">
								<bean:write name="infoLesson" property="fim.timeInMillis" />
							</dt:format>
						</td>
						<td style="text-align:center">
							<bean:write name="infoLesson" property="infoSala.nome" />
						</td>
					</tr>			
				</logic:iterate>
				<bean:define id="elem" name="infoShift" property="infoDisciplinaExecucao.nome" type="java.lang.String"/> 
			</logic:iterate>
		</logic:present>
		
		<logic:notPresent name="infoShiftEnrollment" property="infoShiftEnrollment">
			<logic:empty name="infoShiftEnrollment" property="infoShiftEnrollment">
				<tr>
					<td>
						<span class="error"><bean:message key="message.warning.student.notYet.enroll" /></span>	
					</td>
				</tr>				
			</logic:empty>
		</logic:notPresent>
		
	</table>
</logic:present>