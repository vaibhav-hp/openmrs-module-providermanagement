/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.providermanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.providermanagement.api.ProviderManagementService;
import org.openmrs.module.providermanagement.fragment.controller.ProviderSearchFragmentController;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentActionUiUtils;

import java.util.List;
import java.util.Map;

public class ProviderSearchFragmentControllerTest extends BaseModuleContextSensitiveTest {

    protected static final String XML_DATASET_PATH = "org/openmrs/module/providermanagement/include/";

    protected static final String XML_DATASET = "providerManagement-dataset.xml";

    private UiUtils ui;

    @Before
    public void init() throws Exception {
        this.ui = new FragmentActionUiUtils(null, null, null);
        // execute the provider management test dataset
        executeDataSet(XML_DATASET_PATH + XML_DATASET);

    }

    @Test
    public void getProviders_shouldReturnMatchedProvider() throws Exception {
        ProviderSearchFragmentController controller = new ProviderSearchFragmentController();
        String [] resultFields = new String[] {"gender", "personName.givenName", "personAddress.cityVillage"};

        List<SimpleObject> results = controller.getProviders("2a7", null, resultFields, ui);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("F", results.get(0).get("gender"));
        Assert.assertEquals("Collet", ((Map<String,Object>) results.get(0).get("personName")).get("givenName"));
        Assert.assertEquals("Kapina", ((Map<String,Object>) results.get(0).get("personAddress")).get("cityVillage"));
    }

    @Test
    public void getProviders_shouldReturnProviderFields() throws Exception {
        ProviderSearchFragmentController controller = new ProviderSearchFragmentController();
        String [] resultFields = new String[] {"provider.identifier", "provider.providerRole"};

        List<SimpleObject> results = controller.getProviders("2a7", null, resultFields, ui);
        Assert.assertEquals("2a7", ((Map<String,Object>) results.get(0).get("provider")).get("identifier"));
        Assert.assertEquals("Binome", ((Map<String,Object>) results.get(0).get("provider")).get("providerRole"));
    }
}
