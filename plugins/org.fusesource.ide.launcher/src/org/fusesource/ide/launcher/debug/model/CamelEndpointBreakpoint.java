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
package org.fusesource.ide.launcher.debug.model;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.fusesource.ide.camel.model.AbstractNode;
import org.fusesource.ide.launcher.debug.util.ICamelDebugConstants;

/**
 * Camel Endpoint Breakpoint
 * 
 * @author lhein
 */
public class CamelEndpointBreakpoint extends Breakpoint {
	
	private String projectName;
	private String fileName;
	private String endpointNodeId;
	private String contextId;
	private IResource resource;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public CamelEndpointBreakpoint() {
	}
	
	/**
	 * Constructs a breakpoint on the given resource at the given
	 * camel endpoint.
	 * 
	 * @param resource file on which to set the breakpoint
	 * @param endpoint the endpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public CamelEndpointBreakpoint(final IResource resource, final AbstractNode endpoint, final String projectName, final String fileName)
			throws CoreException {
		this.endpointNodeId = endpoint.getId();
		this.contextId = endpoint.getCamelContextId();
		this.projectName = projectName;
		this.fileName = fileName;
		this.resource = resource;
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
			 */
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(ICamelDebugConstants.ID_CAMEL_MARKER_TYPE);
				marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
				marker.setAttribute(IBreakpoint.PERSISTED, Boolean.TRUE);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_CONTEXTID, contextId);
				marker.setAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_ENDPOINTID, endpointNodeId);
				marker.setAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_PROJECTNAME, projectName);
				marker.setAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_FILENAME, fileName);
				marker.setAttribute(IMarker.MESSAGE, "Camel Breakpoint: " + resource.getName() + " [Endpoint: " + endpointNodeId + "]");
				setMarker(marker);
			}
		};
		run(getMarkerRule(resource), runnable);
		setPersisted(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.Breakpoint#setMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		this.projectName = marker.getAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_PROJECTNAME, projectName);
		this.endpointNodeId = marker.getAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_ENDPOINTID, endpointNodeId);
		this.fileName = marker.getAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_FILENAME, fileName);
		this.contextId = marker.getAttribute(ICamelDebugConstants.MARKER_ATTRIBUTE_CONTEXTID, contextId);
		this.resource = marker.getResource();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IBreakpoint#getModelIdentifier()
	 */
	public String getModelIdentifier() {
		return ICamelDebugConstants.ID_CAMEL_DEBUG_MODEL;
	}
	
	/**
	 * @param endpointNodeId the endpointNodeId to set
	 */
	public void setEndpointNodeId(String endpointNodeId) {
		this.endpointNodeId = endpointNodeId;
	}
	
	/**
	 * @return the endpointNodeId
	 */
	public String getEndpointNodeId() {
		return this.endpointNodeId;
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * @param resource the resource to set
	 */
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	
	/**
	 * @return the resource
	 */
	public IResource getResource() {
		return this.resource;
	}
	
	/**
	 * @return the contextId
	 */
	public String getContextId() {
		return this.contextId;
	}
	
	/**
	 * @param contextId the contextId to set
	 */
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
}
