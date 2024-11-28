import Http from "../../http/NetWorkHttp.ts";

export class SpyHttp implements Http {
    get_argument_url?: string = undefined
    get(url: string): Promise<string[]> {
        this.get_argument_url = url
        return Promise.resolve([]);
    }

    post_argument_url?: string = undefined
    post_argument_body?: string = undefined
    post(url: string, body: string): void {
        this.post_argument_url = url
        this.post_argument_body = body
    }

}

export class StubHttp implements Http {
    get_returnValue: Promise<string[]> = Promise.resolve([])
    get(url: string): Promise<string[]> {
        return this.get_returnValue
    }

    post(url: string, body: string): void {
    }

}