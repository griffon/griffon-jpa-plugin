/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.plugins.jpa

import griffon.util.CallableWithArgs
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Andres Almiray
 */
final class JpaEnhancer {
    private static final String DEFAULT = 'default'
    private static final Logger LOG = LoggerFactory.getLogger(JpaEnhancer)

    private JpaEnhancer() {}
    
    static void enhance(MetaClass mc, JpaProvider provider = DefaultJpaProvider.instance) {
        if (LOG.debugEnabled) LOG.debug("Enhancing $mc with $provider")
        mc.withJpa = {Closure closure ->
            provider.withJpa(DEFAULT, closure)
        }
        mc.withJpa << {String persistenceUnit, Closure closure ->
            provider.withJpa(persistenceUnit, closure)
        }
        mc.withJpa << {CallableWithArgs callable ->
            provider.withJpa(DEFAULT, callable)
        }
        mc.withJpa << {String persistenceUnit, CallableWithArgs callable ->
            provider.withJpa(persistenceUnit, callable)
        }
    }
}