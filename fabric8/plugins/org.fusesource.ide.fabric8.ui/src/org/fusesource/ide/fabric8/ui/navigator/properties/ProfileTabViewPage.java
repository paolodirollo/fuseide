/*******************************************************************************
 * Copyright (c) 2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.fusesource.ide.fabric8.ui.navigator.properties;

import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.fusesource.ide.commons.ui.views.PropertiesPageTabDescriptor;
import org.fusesource.ide.commons.ui.views.TabFolderSupport2;
import org.fusesource.ide.fabric8.ui.navigator.ProfileNode;


public class ProfileTabViewPage extends TabFolderSupport2 {
	private final ProfileNode node;

	public ProfileTabViewPage(ProfileNode node) {
		super(node.getClass().getName(), true);
		this.node = node;
	}

	@Override
	protected ITabDescriptor[] getTabDescriptors() {
		return new ITabDescriptor[] {
				new ProfileDetailsFormTabDescriptor("Details", node),
				new ProfileContainersTabDescriptor("Containers", node),
				new PropertiesPageTabDescriptor(node),
				new ProfileRequirementsFormTabDescriptor("Requirements", node)
		};
	}
}
