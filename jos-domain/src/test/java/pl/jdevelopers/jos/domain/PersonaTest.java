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
 *
 */
package pl.jdevelopers.jos.domain;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author sutra
 */
public class PersonaTest {
    private Persona persona;
    private Attribute attribute;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        persona = new Persona();

        attribute = new Attribute();
        attribute.setId("test Id");
        attribute.setAlias("testAlias");
        attribute.setType("testType");
        attribute.addValue("testValue");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link Persona#Persona()}.
     */
    @Test
    public void testNewPersona() {
        Persona persona = new Persona();
        assertNotNull(persona.getAttributes());
    }

    /**
     * Test method for {@link Persona#Persona(User)}.
     */
    @Test
    public void testNewPersonaWithUser() {
        User user = new User();
        Persona persona = new Persona(user);
        assertNotNull(persona.getAttributes());
    }

    @Test
    public void testToAliasValueMap() {
        Persona persona = new Persona();
        persona.setNickname("My Nickname");
        Map<String, String> actual = persona.toAliasValueMap();
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("nickname", "My Nickname");
        assertEquals(expected, actual);
    }

    @Test
    public void testToTypeValueMap() {
        Persona persona = new Persona();
        persona.setNickname("My Nickname");
        Map<String, String> actual = persona.toTypeValueMap();
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("http://schema.openid.net/namePerson/friendly",
                persona.getNickname());
        expected.put("http://axschema.org/namePerson/friendly",
                persona.getNickname());
        assertEquals(expected, actual);
    }

    /**
     * Test method for {@link Persona#getAttributes()}.
     */
    @Test
    public void testGetAttributes() {
        assertNotNull(persona.getAttributes());
        assertNotNull(attribute);
        persona.addAttribute(attribute);
        assertEquals(1, persona.getAttributes().size());
    }

    /**
     * Test method for
     * {@link Persona#setAttributes(java.util.Set)}.
     */
    @Test
    public void testSetAttributes() {
        Set<Attribute> attributes = new HashSet<Attribute>();
        assertNotNull(attribute);
        attributes.add(attribute);
        persona.setAttributes(attributes);
        assertEquals(1, persona.getAttributes().size());
    }

    /**
     * Test method for
     * {@link Persona#readExternal(java.io.ObjectInput)}
     * and
     * {@link Persona#writeExternal(java.io.ObjectOutput)}
     * .
     */
    @Test
    public void testExternalizable() {
        persona.setAttributes(new AbstractSet<Attribute>() {
            private List<Attribute> attributes = new ArrayList<Attribute>();

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean add(Attribute o) {
                return attributes.add(o);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Iterator<Attribute> iterator() {
                return attributes.iterator();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int size() {
                return attributes.size();
            }
        });

        persona.setId("testId");
        persona.addAttribute(attribute);
        assertEquals(1, persona.getAttributes().size());

        // Serialize
        byte[] bytes = SerializationUtils.serialize(persona);

        // Deserialize
        Persona deserialized = (Persona) SerializationUtils.deserialize(bytes);

        assertEquals(persona, deserialized);
        assertEquals("testId", deserialized.getId());
        assertEquals(persona.getCreationDate(), deserialized.getCreationDate());
        assertEquals(1, deserialized.getAttributes().size());
        assertEquals("testAlias", deserialized.getAttributes().iterator().next().getAlias());
    }
}
