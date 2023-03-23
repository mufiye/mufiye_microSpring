package org.example.ioc.config;

import org.example.ioc.filter.MyTypeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// Custom是使用自定义的filter
@Configuration
@ComponentScan(value = "org.example",
               includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})},
               useDefaultFilters = false)
public class MainConfig_IncludeFilter {
}
