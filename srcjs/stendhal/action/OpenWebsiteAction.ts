/***************************************************************************
 *                   (C) Copyright 2003-2021 - Stendhal                    *
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU Affero General Public License as        *
 *   published by the Free Software Foundation; either version 3 of the    *
 *   License, or (at your option) any later version.                       *
 *                                                                         *
 ***************************************************************************/

import { Action } from "./Action";

/**
 * opens the specified website in the browser
 */
export class OpenWebsiteAction extends Action {
    getMinParams = 0;
    getMaxParams = 0;

    /**
     * creates a OpenWebsiteAction
     * 
     * @param url website to open
     */
    constructor(private url: string) {
        super();
    }

    execute(type: string, params: string[], remainder: string) {
        window.location.href = this.url;
    }

};