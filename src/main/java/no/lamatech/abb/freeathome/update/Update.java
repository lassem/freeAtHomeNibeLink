/**
 * Copyright (c) 2014-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package no.lamatech.abb.freeathome.update;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * XML scheme extension for XMPP update events: abb.com.protocol.update
 * Implementation of update element in the http://abb.com/protocol/update namespace
 * as XEP-0163: Personal Eventing Protocol
 * based on https://sco0ter.bitbucket.io/babbler/xep/pep.html
 *
 * @author ruebox
 *
 *         <update xmlns='http://abb.com/protocol/update'>
 *         <data>
 *         DATA
 *         </data>
 *         </update>
 *
 * @author ruebox
 * @see <a href="http://xmpp.org/extensions/xep-0163.html">XEP-0163: Personal Eventing Protocol</a>
 */
@XmlRootElement(name = "update")
public final class Update {

    /**
     * http://abb.com/protocol/update
     */

    public static final String NAMESPACE = "http://abb.com/protocol/update";

    @XmlElement(required = true)
    private final String data;

    private Update() {
        this.data = null;
    }

    private Update(Builder builder) {
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final String getData() {
        return data;
    }

    @Override
    public final String toString() {
        return data;
    }

    public static final class Builder {

        private String data;

        private Builder() {
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Update build() {
            return new Update(this);
        }
    }
}