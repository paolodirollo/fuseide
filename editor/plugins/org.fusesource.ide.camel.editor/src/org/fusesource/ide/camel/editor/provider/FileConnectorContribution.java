/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.fusesource.ide.camel.editor.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.tb.IImageDecorator;
import org.eclipse.graphiti.tb.ImageDecorator;
import org.fusesource.ide.camel.editor.Activator;
import org.fusesource.ide.camel.editor.features.create.ext.CreateEndpointFigureFeature;
import org.fusesource.ide.camel.editor.provider.ext.ICustomPaletteEntry;
import org.fusesource.ide.camel.editor.provider.ext.PaletteCategoryItemProvider.CATEGORY_TYPE;
import org.fusesource.ide.camel.model.Endpoint;
import org.fusesource.ide.camel.model.connectors.ConnectorDependency;

/**
 * @author lhein
 *
 */
public class FileConnectorContribution implements ICustomPaletteEntry {

    /**
     * 
     */
    public FileConnectorContribution() {
    }

    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#getPaletteCategory()
     */
    @Override
    public CATEGORY_TYPE getPaletteCategory() {
        return CATEGORY_TYPE.COMPONENTS;
    }

    /*
     * (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#newCreateFeature(org.eclipse.graphiti.features.IFeatureProvider)
     */
    @Override
    public ICreateFeature newCreateFeature(IFeatureProvider fp) {
        return new CreateEndpointFigureFeature(fp, "Filesystem", "Enables you to poll a folder or to write into files...", new Endpoint("file:"));
    }

    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#getImageDecorator(java.lang.Object)
     */
    @Override
    public IImageDecorator getImageDecorator(Object object) {
        return new ImageDecorator(ImageProvider.getKeyForSmallIcon("endpoint.png"));
    }

    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#getTypeName()
     */
    @Override
    public String getTypeName() {
        return "fileEndpoint";
    }

    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class type) {
        if (type.isAssignableFrom(Endpoint.class)) return true;
        return false;
    }

    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.editor.provider.ICustomPaletteEntry#getRequiredCapabilities(java.lang.Object)
     */
    @Override
    public List<ConnectorDependency> getRequiredCapabilities(Object object) {
        ArrayList<ConnectorDependency> deps = new ArrayList<ConnectorDependency>();
        
        ConnectorDependency dep = new ConnectorDependency();
        dep.setGroupId("org.apache.camel");
        dep.setArtifactId("camel-core");
        dep.setVersion(Activator.getDefault().getCamelVersion());
        
        deps.add(dep);
        
        return deps;
    }

}