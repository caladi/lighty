/*
 * Copyright (c) 2021 PANTHEON.tech s.r.o. All Rights Reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v10.html
 */

package io.lighty.gnmi.southbound.device.connection;

import gnmi.Gnmi;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.opendaylight.yang.gen.v1.urn.lighty.gnmi.force.capabilities.rev210702.ForceCapabilities;
import org.opendaylight.yang.gen.v1.urn.lighty.gnmi.topology.rev210316.gnmi.connection.parameters.ExtensionsParameters;
import org.opendaylight.yang.gen.v1.urn.lighty.gnmi.topology.rev210316.gnmi.connection.parameters.extensions.parameters.GnmiParameters;

public class ConfigurableParameters {

    private final GnmiParameters gnmiParameters;
    private final ForceCapabilities forceCapabilities;

    public ConfigurableParameters(final ExtensionsParameters extensionsParameters) {
        if (extensionsParameters != null) {
            gnmiParameters = extensionsParameters.getGnmiParameters();
            forceCapabilities = extensionsParameters.augmentation(ForceCapabilities.class);
        } else {
            gnmiParameters = null;
            forceCapabilities = null;
        }
    }

    public Optional<Boolean> getUseModelNamePrefix() {
        if (gnmiParameters != null) {
            return Optional.ofNullable(gnmiParameters.getUseModelNamePrefix());
        }
        return Optional.empty();
    }

    public Optional<GnmiParameters.OverwriteDataType> getOverwriteDataType() {
        if (gnmiParameters != null) {
            return Optional.ofNullable(gnmiParameters.getOverwriteDataType());
        }
        return Optional.empty();
    }

    public Optional<String> getPathTarget() {
        if (gnmiParameters != null) {
            return Optional.ofNullable(gnmiParameters.getPathTarget());
        }
        return Optional.empty();
    }

    public Optional<List<Gnmi.ModelData>> getForceCapabilities() {
        if (forceCapabilities != null) {
            return Optional.of(forceCapabilities.getForceCapability()
                .entrySet()
                .stream()
                .map(model -> model.getValue())
                .map(model -> Gnmi.ModelData.newBuilder()
                    .setName(model.getName())
                    .setVersion(model.getVersion().getValue()).build())
                .collect(Collectors.toList()));
        }
        return Optional.empty();
    }
}
