/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-5-20 01:51:02
 */
package pl.jdevelopers.jos.web.controller;

import org.springframework.web.servlet.ModelAndView;
import pl.jdevelopers.jos.domain.User;
import pl.jdevelopers.jos.service.exception.PersonaInUseException;
import pl.jdevelopers.jos.web.AbstractJosController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sutra Zhou
 */
public class PersonasController extends AbstractJosController {
    /**
     * {@inheritDoc}
     */
    public ModelAndView handleRequest(final HttpServletRequest request,
                                      final HttpServletResponse response) {
        User user = getUser(request);

        // Delete checked personas.
        String[] deletePersonaIds = request
                .getParameterValues("deletePersonaId");
        if (deletePersonaIds != null) {
            try {
                getJosService().deletePersonas(user, deletePersonaIds);
            } catch (PersonaInUseException e) {
                request.setAttribute("error", "persona.error.inUse");
            }
        }

        return new ModelAndView("personas", "personas", getJosService().getPersonas(user));
    }
}
