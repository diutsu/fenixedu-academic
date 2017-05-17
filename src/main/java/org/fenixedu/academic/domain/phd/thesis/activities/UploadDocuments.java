/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.domain.phd.thesis.activities;

import java.io.IOException;
import java.util.List;

import org.fenixedu.academic.domain.caseHandling.PreConditionNotValidException;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.phd.PhdProgramDocumentUploadBean;
import org.fenixedu.academic.domain.phd.thesis.PhdThesisProcess;
import org.fenixedu.bennu.core.domain.User;

public class UploadDocuments extends PhdThesisActivity {

    @Override
    protected void activityPreConditions(PhdThesisProcess process, User userView) {
        if (!process.isAllowedToManageProcess(userView)) {
            throw new PreConditionNotValidException();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PhdThesisProcess executeActivity(PhdThesisProcess process, User userView, Object object) {
        final List<PhdProgramDocumentUploadBean> documents = (List<PhdProgramDocumentUploadBean>) object;

        for (final PhdProgramDocumentUploadBean each : documents) {
            if (each.hasAnyInformation()) {
                try {
                    process.addDocument(each, userView != null ? userView.getPerson() : null);
                } catch (IOException e) {
                    throw new DomainException("error.file");
                }
            }
        }

        return process;
    }

}
