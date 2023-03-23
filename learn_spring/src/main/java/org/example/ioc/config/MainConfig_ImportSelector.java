package org.example.ioc.config;

import org.example.ioc.selector.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportSelector.class)  // MyImportSelector是自定义的selector
public class MainConfig_ImportSelector {
}
