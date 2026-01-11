Combo comboT11 = new Combo(
                "Combo Funda Transparente + Templado iPhone 11",
                "iPhone 11",
                5,
                5500
            );
            comboT11.agregarProducto(funda11Transp);
            comboT11.agregarProducto(templado11);
            
            Combo comboS11 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 11",
                "iPhone 11",
                5,
                6500
            );
            comboS11.agregarProducto(funda11Silicone);
            comboS11.agregarProducto(templado11);

            Combo comboS16 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 16",
                "iPhone 16",
                5,
                6500
            );
            comboS16.agregarProducto(funda16Silicone);
            comboS16.agregarProducto(templado16);
            
            Combo comboT16 = new Combo(
                "Combo Funda Transparente + Templado iPhone 16",
                "iPhone 16",
                5,
                5500
            );
            
            
            comboT16.agregarProducto(funda16Transp);
            comboT16.agregarProducto(templado16);

            inventario.agregarProducto(comboT11);
            inventario.agregarProducto(comboS16);
            inventario.agregarProducto(comboS11);
            inventario.agregarProducto(comboT16);
            
            Combo comboTC11 = new Combo(
                "Combo Funda Transparente + Templado iPhone 11 + Cargador",
                "iPhone 11",
                5,
                17000
            );
            
            comboTC11.agregarProducto(cargador);
            comboTC11.agregarProducto(funda11Transp);
            comboTC11.agregarProducto(templado11);
            
            Combo comboSC11 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 11 + Cargador",
                "iPhone 11",
                5,
                18000
            );
            
            comboSC11.agregarProducto(cargador);
            comboSC11.agregarProducto(funda11Silicone);
            comboSC11.agregarProducto(templado11);
            
            inventario.agregarProducto(comboTC11);
            inventario.agregarProducto(comboSC11);
            
            Combo comboST11 = new Combo(
                "Combo Fundas S+T iPhone 11",
                "iPhone 11",
                5,
                7500
            );
            
            comboST11.agregarProducto(funda11Silicone);
            comboST11.agregarProducto(funda11Transp);
            
            Combo comboST13 = new Combo(
                "Combo Fundas S+T iPhone 13",
                "iPhone 13",
                5,
                7500
            );
            
            comboST13.agregarProducto(funda13Silicone);
            comboST13.agregarProducto(funda13Transp);
            
            Combo comboST16 = new Combo(
                "Combo Fundas S+T iPhone 16",
                "iPhone 16",
                5,
                7500
            );
            
            comboST16.agregarProducto(funda16Silicone);
            comboST16.agregarProducto(funda16Transp);
            
            Combo comboSS16 = new Combo(
                "Combo Fundas S+S iPhone 16",
                "iPhone 16",
                5,
                8500
            );
            
            comboSS16.agregarProducto(funda16Silicone);
            comboSS16.agregarProducto(funda16Silicone);
            
            Combo comboSS11 = new Combo(
                "Combo Fundas S+S iPhone 11",
                "iPhone 11",
                5,
                8500
            );
            
            comboSS11.agregarProducto(funda11Silicone);
            comboSS11.agregarProducto(funda11Silicone);
            
            Combo comboSS13 = new Combo(
                "Combo Fundas S+S iPhone 13",
                "iPhone 13",
                5,
                8500
            );
            
            comboSS13.agregarProducto(funda13Silicone);
            comboSS13.agregarProducto(funda13Silicone);
            
            inventario.agregarProducto(comboST11);
            inventario.agregarProducto(comboST13);
            inventario.agregarProducto(comboST16);
            inventario.agregarProducto(comboSS13);
            inventario.agregarProducto(comboSS11);
            inventario.agregarProducto(comboSS16);