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
package org.fenixedu.academic.service.services.thesis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.person.RoleType;
import org.fenixedu.academic.domain.thesis.Thesis;
import org.fenixedu.academic.domain.thesis.ThesisFile;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.bennu.core.security.Authenticate;

public abstract class CreateThesisFile {

    public ThesisFile run(Thesis thesis, InputStream file, String fileName, String title, String subTitle, Locale language)
            throws FenixServiceException, IOException {

        if (!thesis.isWaitingConfirmation()
                && !RoleType.SCIENTIFIC_COUNCIL.isMember(Authenticate.getUser().getPerson().getUser())) {
            throw new DomainException("thesis.files.submit.unavailable");
        }

        if (!thesis.isDeclarationAccepted()
                && !RoleType.SCIENTIFIC_COUNCIL.isMember(Authenticate.getUser().getPerson().getUser())) {
            throw new DomainException("thesis.files.submit.unavailable");
        }

        removePreviousFile(thesis);


        ThesisFile thesisFile = new ThesisFile(fileName, fileName, file);

        updateThesis(thesis, thesisFile, title, subTitle, language, fileName);

        return thesisFile;
    }

    protected abstract void removePreviousFile(Thesis thesis);

    protected abstract void updateThesis(Thesis thesis, ThesisFile thesisFile, String title, String subTitle, Locale language,
            String fileName) throws FenixServiceException, IOException;

}
